import type {NativeStackScreenProps} from '@react-navigation/native-stack';

export type RootStackParamList = {
  Welcome: undefined;
  Browse: undefined;
  Search: undefined;
  Featured: undefined;
  Categories: undefined;
  ProductDetail: {source: 'featured' | 'categories'};
  Reviews: {source: 'featured' | 'categories'};
  Cart: undefined;
  Wishlist: undefined;
  CheckoutGuest: undefined;
  CheckoutSignIn: undefined;
  PaymentCard: undefined;
  PaymentApplePay: undefined;
  PaymentPayPal: undefined;
  Confirmation: undefined;
};

export type ScreenProps<T extends keyof RootStackParamList> =
  NativeStackScreenProps<RootStackParamList, T>;
