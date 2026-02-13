import React from 'react';
import {ScreenContainer, PrimaryButton, SecondaryButton} from '../components';
import {Colors} from '../utils/colors';
import type {ScreenProps} from '../navigation/types';

export const WishlistScreen: React.FC<ScreenProps<'Wishlist'>> = ({navigation}) => {
  return (
    <ScreenContainer
      screenName="Wishlist"
      title="Wishlist"
      subtitle="Items you've saved for later"
      step={5}
      icon="💝"
      color={Colors.wishlist}>
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
