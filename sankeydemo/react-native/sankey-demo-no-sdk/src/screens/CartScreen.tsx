import React from 'react';
import {ScreenContainer, PrimaryButton, SecondaryButton} from '../components';
import {Colors} from '../utils/colors';
import type {ScreenProps} from '../navigation/types';

export const CartScreen: React.FC<ScreenProps<'Cart'>> = ({navigation}) => {
  return (
    <ScreenContainer
      screenName="Cart"
      title="Shopping Cart"
      subtitle="Review your items before checkout"
      step={5}
      icon="🛒"
      color={Colors.cart}>
      <PrimaryButton
        title="Guest Checkout"
        icon="👤"
        onPress={() => navigation.navigate('CheckoutGuest')}
      />
      <SecondaryButton
        title="Sign In to Checkout"
        icon="🔐"
        onPress={() => navigation.navigate('CheckoutSignIn')}
      />
    </ScreenContainer>
  );
};
