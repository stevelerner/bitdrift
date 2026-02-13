import React from 'react';
import {ScreenContainer, PrimaryButton} from '../components';
import {Colors} from '../utils/colors';
import type {ScreenProps} from '../navigation/types';

export const PaymentCardScreen: React.FC<ScreenProps<'PaymentCard'>> = ({
  navigation,
}) => {
  return (
    <ScreenContainer
      screenName="PaymentCard"
      title="Card Payment"
      subtitle="Enter your card details securely"
      step={7}
      icon="💳"
      color={Colors.paymentCard}>
      <PrimaryButton
        title="Complete Purchase"
        icon="✅"
        onPress={() => navigation.navigate('Confirmation')}
      />
    </ScreenContainer>
  );
};
