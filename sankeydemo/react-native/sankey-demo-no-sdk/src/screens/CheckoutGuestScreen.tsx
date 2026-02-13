import React from 'react';
import {ScreenContainer, PrimaryButton, SecondaryButton} from '../components';
import {Colors} from '../utils/colors';
import type {ScreenProps} from '../navigation/types';

export const CheckoutGuestScreen: React.FC<ScreenProps<'CheckoutGuest'>> = ({
  navigation,
}) => {
  return (
    <ScreenContainer
      screenName="CheckoutGuest"
      title="Guest Checkout"
      subtitle="Complete your purchase without signing in"
      step={6}
      icon="👤"
      color={Colors.checkoutGuest}>
      <PrimaryButton
        title="Pay with Card"
        icon="💳"
        onPress={() => navigation.navigate('PaymentCard')}
      />
      <SecondaryButton
        title="Pay with Apple Pay"
        icon="🍎"
        onPress={() => navigation.navigate('PaymentApplePay')}
      />
    </ScreenContainer>
  );
};
