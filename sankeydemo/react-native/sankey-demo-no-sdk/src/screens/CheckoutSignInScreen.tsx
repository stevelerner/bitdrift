import React from 'react';
import {ScreenContainer, PrimaryButton, SecondaryButton} from '../components';
import {Colors} from '../utils/colors';
import type {ScreenProps} from '../navigation/types';

export const CheckoutSignInScreen: React.FC<ScreenProps<'CheckoutSignIn'>> = ({
  navigation,
}) => {
  return (
    <ScreenContainer
      screenName="CheckoutSignIn"
      title="Sign In Checkout"
      subtitle="Access your saved payment methods"
      step={6}
      icon="🔐"
      color={Colors.checkoutSignIn}>
      <PrimaryButton
        title="Pay with Card"
        icon="💳"
        onPress={() => navigation.navigate('PaymentCard')}
      />
      <SecondaryButton
        title="Pay with PayPal"
        icon="🅿️"
        onPress={() => navigation.navigate('PaymentPayPal')}
      />
    </ScreenContainer>
  );
};
