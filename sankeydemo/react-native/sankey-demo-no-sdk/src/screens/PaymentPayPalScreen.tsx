import React from 'react';
import {ScreenContainer, PrimaryButton} from '../components';
import {Colors} from '../utils/colors';
import type {ScreenProps} from '../navigation/types';

export const PaymentPayPalScreen: React.FC<ScreenProps<'PaymentPayPal'>> = ({
  navigation,
}) => {
  return (
    <ScreenContainer
      screenName="PaymentPayPal"
      title="PayPal"
      subtitle="Pay securely with PayPal"
      step={7}
      icon="🅿️"
      color={Colors.paymentPayPal}>
      <PrimaryButton
        title="Complete Purchase"
        icon="✅"
        onPress={() => navigation.navigate('Confirmation')}
      />
    </ScreenContainer>
  );
};
