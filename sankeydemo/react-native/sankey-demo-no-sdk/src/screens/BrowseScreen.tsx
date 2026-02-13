import React from 'react';
import {ScreenContainer, PrimaryButton, SecondaryButton} from '../components';
import {Colors} from '../utils/colors';
import type {ScreenProps} from '../navigation/types';

export const BrowseScreen: React.FC<ScreenProps<'Browse'>> = ({navigation}) => {
  return (
    <ScreenContainer
      screenName="Browse"
      title="Browse Products"
      subtitle="Explore our curated collection of products"
      step={2}
      icon="🛒"
      color={Colors.browse}>
      <PrimaryButton
        title="View Featured"
        icon="⭐"
        onPress={() => navigation.navigate('Featured')}
      />
      <SecondaryButton
        title="Browse Categories"
        icon="📂"
        onPress={() => navigation.navigate('Categories')}
      />
    </ScreenContainer>
  );
};
