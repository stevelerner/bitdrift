import React from 'react';
import {View, Text, StyleSheet, SafeAreaView} from 'react-native';
import {StepIndicator, PrimaryButton} from '../components';
import {Colors} from '../utils/colors';
import {ScreenLogger} from '../utils/logger';
import type {ScreenProps} from '../navigation/types';

export const ConfirmationScreen: React.FC<ScreenProps<'Confirmation'>> = ({
  navigation,
}) => {
  React.useEffect(() => {
    ScreenLogger.logScreenView('Confirmation');
  }, []);

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.content}>
        <StepIndicator currentStep={7} activeColor={Colors.white} />

        <View style={styles.iconContainer}>
          <Text style={styles.icon}>🎉</Text>
        </View>

        <Text style={styles.title}>Order Confirmed!</Text>
        <Text style={styles.subtitle}>
          Thank you for your purchase. Your order is being processed.
        </Text>

        <View style={styles.orderInfo}>
          <Text style={styles.orderLabel}>Order Number</Text>
          <Text style={styles.orderNumber}>#DEMO-{Math.floor(Math.random() * 90000) + 10000}</Text>
        </View>

        <View style={styles.buttonsContainer}>
          <PrimaryButton
            title="Start New Journey"
            icon="🏠"
            onPress={() => navigation.navigate('Welcome')}
          />
        </View>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.confirmation,
  },
  content: {
    flex: 1,
    paddingHorizontal: 24,
    paddingTop: 20,
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
    marginVertical: 32,
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
    marginBottom: 32,
    paddingHorizontal: 20,
  },
  orderInfo: {
    backgroundColor: 'rgba(255, 255, 255, 0.2)',
    borderRadius: 12,
    padding: 20,
    alignItems: 'center',
    marginBottom: 32,
  },
  orderLabel: {
    fontSize: 14,
    color: 'rgba(255, 255, 255, 0.8)',
    marginBottom: 8,
  },
  orderNumber: {
    fontSize: 24,
    fontWeight: 'bold',
    color: Colors.white,
  },
  buttonsContainer: {
    width: '100%',
    marginTop: 'auto',
  },
});
