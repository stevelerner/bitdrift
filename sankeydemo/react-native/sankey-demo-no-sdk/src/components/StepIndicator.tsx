import React from 'react';
import {View, StyleSheet} from 'react-native';
import {Colors} from '../utils/colors';

type StepIndicatorProps = {
  currentStep: number;
  totalSteps?: number;
  activeColor: string;
};

export const StepIndicator: React.FC<StepIndicatorProps> = ({
  currentStep,
  totalSteps = 7,
  activeColor,
}) => {
  return (
    <View style={styles.container}>
      {Array.from({length: totalSteps}, (_, index) => (
        <View
          key={index}
          style={[
            styles.dot,
            index < currentStep
              ? {backgroundColor: activeColor}
              : {backgroundColor: Colors.lightGray, borderColor: Colors.gray, borderWidth: 1},
          ]}
        />
      ))}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    gap: 8,
    marginVertical: 16,
  },
  dot: {
    width: 12,
    height: 12,
    borderRadius: 6,
  },
});
