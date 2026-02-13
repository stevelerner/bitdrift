import React from 'react';
import {ScreenContainer, PrimaryButton, SecondaryButton} from '../components';
import {Colors} from '../utils/colors';
import type {ScreenProps} from '../navigation/types';

export const ReviewsScreen: React.FC<ScreenProps<'Reviews'>> = ({navigation}) => {
  return (
    <ScreenContainer
      screenName="Reviews"
      title="Customer Reviews"
      subtitle="See what others are saying"
      step={4}
      icon="💬"
      color={Colors.reviews}>
      <PrimaryButton
        title="Add to Cart"
        icon="🛒"
        onPress={() => navigation.navigate('Cart')}
      />
      <SecondaryButton
        title="Add to Wishlist"
        icon="💝"
        onPress={() => navigation.navigate('Wishlist')}
      />
    </ScreenContainer>
  );
};
