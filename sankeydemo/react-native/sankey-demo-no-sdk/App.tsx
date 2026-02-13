import React, {useRef, useEffect} from 'react';
import {View, StyleSheet, StatusBar} from 'react-native';
import {NavigationContainer, NavigationContainerRef} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import {SafeAreaProvider} from 'react-native-safe-area-context';

import {SimulationProvider, useSimulation} from './src/context/SimulationContext';
import {SimulationOverlay} from './src/components';
import type {RootStackParamList} from './src/navigation/types';

import {
  WelcomeScreen,
  BrowseScreen,
  SearchScreen,
  FeaturedScreen,
  CategoriesScreen,
  ProductDetailScreen,
  ReviewsScreen,
  CartScreen,
  WishlistScreen,
  CheckoutGuestScreen,
  CheckoutSignInScreen,
  PaymentCardScreen,
  PaymentApplePayScreen,
  PaymentPayPalScreen,
  ConfirmationScreen,
} from './src/screens';

const Stack = createNativeStackNavigator<RootStackParamList>();

const AppNavigator: React.FC = () => {
  const navigationRef = useRef<NavigationContainerRef<RootStackParamList>>(null);
  const {setNavigationRef} = useSimulation();

  useEffect(() => {
    if (navigationRef.current) {
      setNavigationRef(navigationRef.current);
    }
  }, [setNavigationRef]);

  return (
    <View style={styles.container}>
      <StatusBar barStyle="light-content" />
      <NavigationContainer ref={navigationRef}>
        <Stack.Navigator
          screenOptions={{
            headerShown: false,
            animation: 'slide_from_right',
          }}>
          <Stack.Screen name="Welcome" component={WelcomeScreen} />
          <Stack.Screen name="Browse" component={BrowseScreen} />
          <Stack.Screen name="Search" component={SearchScreen} />
          <Stack.Screen name="Featured" component={FeaturedScreen} />
          <Stack.Screen name="Categories" component={CategoriesScreen} />
          <Stack.Screen name="ProductDetail" component={ProductDetailScreen} />
          <Stack.Screen name="Reviews" component={ReviewsScreen} />
          <Stack.Screen name="Cart" component={CartScreen} />
          <Stack.Screen name="Wishlist" component={WishlistScreen} />
          <Stack.Screen name="CheckoutGuest" component={CheckoutGuestScreen} />
          <Stack.Screen name="CheckoutSignIn" component={CheckoutSignInScreen} />
          <Stack.Screen name="PaymentCard" component={PaymentCardScreen} />
          <Stack.Screen name="PaymentApplePay" component={PaymentApplePayScreen} />
          <Stack.Screen name="PaymentPayPal" component={PaymentPayPalScreen} />
          <Stack.Screen name="Confirmation" component={ConfirmationScreen} />
        </Stack.Navigator>
      </NavigationContainer>
      <SimulationOverlay />
    </View>
  );
};

const App: React.FC = () => {
  return (
    <SafeAreaProvider>
      <SimulationProvider>
        <AppNavigator />
      </SimulationProvider>
    </SafeAreaProvider>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default App;
