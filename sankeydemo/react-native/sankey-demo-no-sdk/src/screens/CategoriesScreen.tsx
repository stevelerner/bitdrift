import React from 'react';
import {ScreenContainer, PrimaryButton, SecondaryButton} from '../components';
import {Colors} from '../utils/colors';
import type {ScreenProps} from '../navigation/types';

export const CategoriesScreen: React.FC<ScreenProps<'Categories'>> = ({navigation}) => {
  return (
    <ScreenContainer
      screenName="Categories"
      title="Categories"
      subtitle="Browse products by category"
      step={3}
      icon="📂"
      color={Colors.categories}>
      <PrimaryButton
        title="View Details"
        icon="📋"
        onPress={() => navigation.navigate('ProductDetail', {source: 'categories'})}
      />
      <SecondaryButton
        title="Read Reviews"
        icon="💬"
        onPress={() => navigation.navigate('Reviews', {source: 'categories'})}
      />
    </ScreenContainer>
  );
};
