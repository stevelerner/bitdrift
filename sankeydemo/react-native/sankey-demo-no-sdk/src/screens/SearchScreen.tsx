import React from 'react';
import {ScreenContainer, PrimaryButton, SecondaryButton} from '../components';
import {Colors} from '../utils/colors';
import type {ScreenProps} from '../navigation/types';

export const SearchScreen: React.FC<ScreenProps<'Search'>> = ({navigation}) => {
  return (
    <ScreenContainer
      screenName="Search"
      title="Search Products"
      subtitle="Find exactly what you're looking for"
      step={2}
      icon="🔍"
      color={Colors.search}>
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
