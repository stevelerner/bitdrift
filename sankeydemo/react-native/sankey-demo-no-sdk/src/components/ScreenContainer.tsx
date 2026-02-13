import React, {useEffect} from 'react';
import {View, Text, StyleSheet, SafeAreaView} from 'react-native';
import {StepIndicator} from './StepIndicator';
import {Colors} from '../utils/colors';
import {ScreenLogger} from '../utils/logger';

type ScreenContainerProps = {
  screenName: string;
  title: string;
  subtitle: string;
  step: number;
  icon: string;
  color: string;
  children: React.ReactNode;
};

export const ScreenContainer: React.FC<ScreenContainerProps> = ({
  screenName,
  title,
  subtitle,
  step,
  icon,
  color,
  children,
}) => {
  useEffect(() => {
    ScreenLogger.logScreenView(screenName);
  }, [screenName]);

  return (
    <SafeAreaView style={[styles.container, {backgroundColor: color}]}>
      <View style={styles.content}>
        <StepIndicator currentStep={step} activeColor={Colors.white} />

        <View style={styles.iconContainer}>
          <Text style={styles.icon}>{icon}</Text>
        </View>

        <Text style={styles.title}>{title}</Text>
        <Text style={styles.subtitle}>{subtitle}</Text>

        <View style={styles.buttonsContainer}>{children}</View>
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  content: {
    flex: 1,
    paddingHorizontal: 24,
    paddingTop: 20,
    paddingBottom: 40,
    alignItems: 'center',
  },
  iconContainer: {
    width: 100,
    height: 100,
    borderRadius: 50,
    backgroundColor: 'rgba(255, 255, 255, 0.3)',
    justifyContent: 'center',
    alignItems: 'center',
    marginVertical: 24,
  },
  icon: {
    fontSize: 48,
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: Colors.white,
    textAlign: 'center',
    marginBottom: 8,
  },
  subtitle: {
    fontSize: 16,
    color: 'rgba(255, 255, 255, 0.8)',
    textAlign: 'center',
    marginBottom: 32,
  },
  buttonsContainer: {
    width: '100%',
    marginTop: 'auto',
  },
});
