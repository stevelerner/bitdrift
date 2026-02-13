import React, {createContext, useContext, useState, useRef, useCallback} from 'react';
import type {NavigationContainerRef} from '@react-navigation/native';
import type {RootStackParamList} from '../navigation/types';
import {ScreenLogger} from '../utils/logger';

type SimulationState = {
  isSimulating: boolean;
  currentRun: number;
  totalRuns: number;
  isInfinite: boolean;
};

type SimulationContextType = SimulationState & {
  startSimulation: (runs: number) => void;
  startInfiniteSimulation: () => void;
  cancelSimulation: () => void;
  setNavigationRef: (ref: NavigationContainerRef<RootStackParamList>) => void;
};

const SimulationContext = createContext<SimulationContextType | null>(null);

export const useSimulation = () => {
  const context = useContext(SimulationContext);
  if (!context) {
    throw new Error('useSimulation must be used within SimulationProvider');
  }
  return context;
};

export const SimulationProvider: React.FC<{children: React.ReactNode}> = ({
  children,
}) => {
  const [state, setState] = useState<SimulationState>({
    isSimulating: false,
    currentRun: 0,
    totalRuns: 0,
    isInfinite: false,
  });

  const navigationRef = useRef<NavigationContainerRef<RootStackParamList> | null>(null);
  const cancelledRef = useRef(false);

  const setNavigationRef = useCallback(
    (ref: NavigationContainerRef<RootStackParamList>) => {
      navigationRef.current = ref;
    },
    [],
  );

  const delay = (ms: number) => new Promise(resolve => setTimeout(resolve, ms));

  const randomChoice = <T,>(options: T[]): T =>
    options[Math.floor(Math.random() * options.length)];

  const runSingleJourney = async () => {
    const nav = navigationRef.current;
    if (!nav || cancelledRef.current) return;

    const stepDelay = 50;

    // Step 1: Welcome -> Browse or Search
    const step2 = randomChoice(['Browse', 'Search'] as const);
    nav.navigate(step2);
    await delay(stepDelay);
    if (cancelledRef.current) return;

    // Step 2: Browse/Search -> Featured or Categories
    const step3 = randomChoice(['Featured', 'Categories'] as const);
    nav.navigate(step3);
    await delay(stepDelay);
    if (cancelledRef.current) return;

    // Step 3: Featured/Categories -> ProductDetail or Reviews
    const source = step3.toLowerCase() as 'featured' | 'categories';
    const step4 = randomChoice(['ProductDetail', 'Reviews'] as const);
    nav.navigate(step4, {source});
    await delay(stepDelay);
    if (cancelledRef.current) return;

    // Step 4: ProductDetail/Reviews -> Cart or Wishlist
    const step5 = randomChoice(['Cart', 'Wishlist'] as const);
    nav.navigate(step5);
    await delay(stepDelay);
    if (cancelledRef.current) return;

    // Step 5: Cart/Wishlist -> CheckoutGuest or CheckoutSignIn
    const checkoutType = randomChoice(['CheckoutGuest', 'CheckoutSignIn'] as const);
    nav.navigate(checkoutType);
    await delay(stepDelay);
    if (cancelledRef.current) return;

    // Step 6: Checkout -> Payment Method
    let paymentMethod: 'PaymentCard' | 'PaymentApplePay' | 'PaymentPayPal';
    if (checkoutType === 'CheckoutGuest') {
      paymentMethod = randomChoice(['PaymentCard', 'PaymentApplePay'] as const);
    } else {
      paymentMethod = randomChoice(['PaymentCard', 'PaymentPayPal'] as const);
    }
    nav.navigate(paymentMethod);
    await delay(stepDelay);
    if (cancelledRef.current) return;

    // Step 7: Payment -> Confirmation
    nav.navigate('Confirmation');
    await delay(stepDelay);
    if (cancelledRef.current) return;

    // Return to Welcome
    nav.navigate('Welcome');
    await delay(stepDelay);
  };

  const runSimulation = async (runs: number, infinite: boolean) => {
    cancelledRef.current = false;
    setState({
      isSimulating: true,
      currentRun: 0,
      totalRuns: runs,
      isInfinite: infinite,
    });

    ScreenLogger.logSimulationStart(runs);

    let currentRun = 0;
    while ((infinite || currentRun < runs) && !cancelledRef.current) {
      currentRun++;
      setState(prev => ({...prev, currentRun}));
      await runSingleJourney();
    }

    ScreenLogger.logSimulationEnd(currentRun);

    setState({
      isSimulating: false,
      currentRun: 0,
      totalRuns: 0,
      isInfinite: false,
    });
  };

  const startSimulation = useCallback((runs: number) => {
    runSimulation(runs, false);
  }, []);

  const startInfiniteSimulation = useCallback(() => {
    runSimulation(Infinity, true);
  }, []);

  const cancelSimulation = useCallback(() => {
    cancelledRef.current = true;
  }, []);

  return (
    <SimulationContext.Provider
      value={{
        ...state,
        startSimulation,
        startInfiniteSimulation,
        cancelSimulation,
        setNavigationRef,
      }}>
      {children}
    </SimulationContext.Provider>
  );
};
