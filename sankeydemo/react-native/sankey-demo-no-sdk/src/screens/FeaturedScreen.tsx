import React from 'react';
import {ScreenContainer, PrimaryButton, SecondaryButton} from '../components';
import {Colors} from '../utils/colors';
import type {ScreenProps} from '../navigation/types';

export const FeaturedScreen: React.FC<ScreenProps<'Featured'>> = ({navigation}) => {
  return (
    <ScreenContainer
      screenName="Featured"
      title="Featured Products"
      subtitle="Our top picks just for you"
      step={3}
      icon="⭐"
      color={Colors.featured}>
      <PrimaryButton
        title="View Details"
        icon="📋"
        onPress={() => navigation.navigate('ProductDetail', {source: 'featured'})}
      />
      <SecondaryButton
        title="Read Reviews"
        icon="💬"
        onPress={() => navigation.navigate('Reviews', {source: 'featured'})}
      />
    </ScreenContainer>
  );
};
