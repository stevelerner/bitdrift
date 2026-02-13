import React from 'react';
import {View, Text, StyleSheet, TouchableOpacity, ActivityIndicator} from 'react-native';
import {useSimulation} from '../context/SimulationContext';
import {Colors} from '../utils/colors';

export const SimulationOverlay: React.FC = () => {
  const {isSimulating, currentRun, totalRuns, isInfinite, cancelSimulation} =
    useSimulation();

  if (!isSimulating) {
    return null;
  }

  const progressText = isInfinite
    ? `Run ${currentRun} / ∞`
    : `Run ${currentRun} / ${totalRuns}`;

  return (
    <View style={styles.container}>
      <View style={styles.card}>
        <ActivityIndicator size="small" color={Colors.black} style={styles.spinner} />
        <Text style={styles.text}>{progressText}</Text>
        <TouchableOpacity onPress={cancelSimulation} style={styles.cancelButton}>
          <Text style={styles.cancelText}>Cancel</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    position: 'absolute',
    bottom: 40,
    left: 0,
    right: 0,
    alignItems: 'center',
    zIndex: 1000,
  },
  card: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: Colors.white,
    borderRadius: 12,
    paddingVertical: 12,
    paddingHorizontal: 20,
    shadowColor: Colors.black,
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  spinner: {
    marginRight: 12,
  },
  text: {
    fontSize: 16,
    fontWeight: '600',
    color: Colors.black,
  },
  cancelButton: {
    marginLeft: 16,
    paddingVertical: 4,
    paddingHorizontal: 12,
    backgroundColor: Colors.lightGray,
    borderRadius: 8,
  },
  cancelText: {
    fontSize: 14,
    color: Colors.black,
  },
});
