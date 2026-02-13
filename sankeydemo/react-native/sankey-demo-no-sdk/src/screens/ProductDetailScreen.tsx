import React from 'react';
import {ScreenContainer, PrimaryButton, SecondaryButton} from '../components';
import {Colors} from '../utils/colors';
import type {ScreenProps} from '../navigation/types';

export const ProductDetailScreen: React.FC<ScreenProps<'ProductDetail'>> = ({
  navigation,
}) => {
  return (
    <ScreenContainer
      screenName="ProductDetail"
      title="Product Details"
      subtitle="Everything you need to know about this product"
      step={4}
      icon="📋"
      color={Colors.productDetail}>
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
