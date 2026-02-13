import React from 'react';
import {View, Text, StyleSheet, SafeAreaView} from 'react-native';
import {PrimaryButton, SecondaryButton, SimButton} from '../components';
import {useSimulation} from '../context/SimulationContext';
import {Colors} from '../utils/colors';
import {ScreenLogger} from '../utils/logger';
import type {ScreenProps} from '../navigation/types';

export const WelcomeScreen: React.FC<ScreenProps<'Welcome'>> = ({navigation}) => {
  const {startSimulation, startInfiniteSimulation, isSimulating} = useSimulation();

  React.useEffect(() => {
    ScreenLogger.logScreenView('Welcome');
  }, []);

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.content}>
        <View style={styles.iconContainer}>
          <Text style={styles.icon}>🛍️</Text>
        </View>

        <Text style={styles.title}>Welcome to ShopDemo</Text>
        <Text style={styles.subtitle}>
          Experience multiple shopping journeys with our demo app
        </Text>

        <View style={styles.buttonsContainer}>
          <PrimaryButton
            title="Browse Products"
            icon="🔍"
            onPress={() => navigation.navigate('Browse')}
            disabled={isSimulating}
          />
          <SecondaryButton
            title="Search Products"
            icon="🔎"
            onPress={() => navigation.navigate('Search')}
            disabled={isSimulating}
          />

          <View style={styles.simContainer}>
            <Text style={styles.simLabel}>Simulate Journeys:</Text>
            <View style={styles.simButtons}>
              <SimButton
                title="Sim 10"
                color={Colors.browse}
                onPress={() => startSimulation(10)}
              />
              <SimButton
                title="Sim 100"
                color={Colors.search}
                onPress={() => startSimulation(100)}
              />
              <SimButton
                title="∞"
                color={Colors.categories}
                onPress={startInfiniteSimulation}
              />
            </View>
          </View>
        </View>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.welcome,
  },
  content: {
    flex: 1,
    paddingHorizontal: 24,
    paddingTop: 40,
    paddingBottom: 40,
    alignItems: 'center',
  },
  iconContainer: {
    width: 120,
    height: 120,
    borderRadius: 60,
    backgroundColor: 'rgba(255, 255, 255, 0.3)',
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 32,
  },
  icon: {
    fontSize: 56,
  },
  title: {
    fontSize: 32,
    fontWeight: 'bold',
    color: Colors.white,
    textAlign: 'center',
    marginBottom: 12,
  },
  subtitle: {
    fontSize: 16,
    color: 'rgba(255, 255, 255, 0.8)',
    textAlign: 'center',
    marginBottom: 48,
    paddingHorizontal: 20,
  },
  buttonsContainer: {
    width: '100%',
    marginTop: 'auto',
  },
  simContainer: {
    marginTop: 32,
    alignItems: 'center',
  },
  simLabel: {
    fontSize: 14,
    color: 'rgba(255, 255, 255, 0.8)',
    marginBottom: 12,
  },
  simButtons: {
    flexDirection: 'row',
    justifyContent: 'center',
  },
});
