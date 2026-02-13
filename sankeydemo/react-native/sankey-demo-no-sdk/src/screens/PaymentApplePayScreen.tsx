import React from 'react';
import {ScreenContainer, PrimaryButton} from '../components';
import {Colors} from '../utils/colors';
import type {ScreenProps} from '../navigation/types';

export const PaymentApplePayScreen: React.FC<ScreenProps<'PaymentApplePay'>> = ({
  navigation,
}) => {
  return (
    <ScreenContainer
      screenName="PaymentApplePay"
      title="Apple Pay"
      subtitle="Pay securely with Apple Pay"
      step={7}
      icon="🍎"
      color={Colors.paymentApplePay}>
      <PrimaryButton
        title="Complete Purchase"
        icon="✅"
        onPress={() => navigation.navigate('Confirmation')}
      />
    </ScreenContainer>
  );
};
