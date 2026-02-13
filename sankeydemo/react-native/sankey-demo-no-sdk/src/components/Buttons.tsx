import React from 'react';
import {TouchableOpacity, Text, StyleSheet, View} from 'react-native';
import {Colors} from '../utils/colors';

type ButtonProps = {
  title: string;
  onPress: () => void;
  icon?: string;
  disabled?: boolean;
};

export const PrimaryButton: React.FC<ButtonProps> = ({
  title,
  onPress,
  icon,
  disabled = false,
}) => {
  return (
    <TouchableOpacity
      style={[styles.primaryButton, disabled && styles.disabled]}
      onPress={onPress}
      disabled={disabled}
      activeOpacity={0.7}>
      <View style={styles.buttonContent}>
        {icon && <Text style={styles.primaryIcon}>{icon}</Text>}
        <Text style={styles.primaryText}>{title}</Text>
        <Text style={styles.primaryArrow}>→</Text>
      </View>
    </TouchableOpacity>
  );
};

export const SecondaryButton: React.FC<ButtonProps> = ({
  title,
  onPress,
  icon,
  disabled = false,
}) => {
  return (
    <TouchableOpacity
      style={[styles.secondaryButton, disabled && styles.disabled]}
      onPress={onPress}
      disabled={disabled}
      activeOpacity={0.7}>
      <View style={styles.buttonContent}>
        {icon && <Text style={styles.secondaryIcon}>{icon}</Text>}
        <Text style={styles.secondaryText}>{title}</Text>
        <Text style={styles.secondaryArrow}>→</Text>
      </View>
    </TouchableOpacity>
  );
};

type SimButtonProps = {
  title: string;
  onPress: () => void;
  color: string;
};

export const SimButton: React.FC<SimButtonProps> = ({title, onPress, color}) => {
  return (
    <TouchableOpacity
      style={[styles.simButton, {backgroundColor: color}]}
      onPress={onPress}
      activeOpacity={0.7}>
      <Text style={styles.simButtonText}>{title}</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  primaryButton: {
    backgroundColor: Colors.black,
    borderRadius: 12,
    paddingVertical: 16,
    paddingHorizontal: 20,
    marginVertical: 6,
  },
  secondaryButton: {
    backgroundColor: Colors.white,
    borderRadius: 12,
    paddingVertical: 16,
    paddingHorizontal: 20,
    marginVertical: 6,
    borderWidth: 2,
    borderColor: Colors.black,
  },
  disabled: {
    opacity: 0.5,
  },
  buttonContent: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  primaryIcon: {
    fontSize: 20,
    marginRight: 8,
  },
  primaryText: {
    color: Colors.white,
    fontSize: 16,
    fontWeight: '600',
    flex: 1,
  },
  primaryArrow: {
    color: Colors.white,
    fontSize: 18,
  },
  secondaryIcon: {
    fontSize: 20,
    marginRight: 8,
  },
  secondaryText: {
    color: Colors.black,
    fontSize: 16,
    fontWeight: '600',
    flex: 1,
  },
  secondaryArrow: {
    color: Colors.black,
    fontSize: 18,
  },
  simButton: {
    borderRadius: 8,
    paddingVertical: 10,
    paddingHorizontal: 16,
    marginHorizontal: 4,
  },
  simButtonText: {
    color: Colors.white,
    fontSize: 14,
    fontWeight: '600',
  },
});
