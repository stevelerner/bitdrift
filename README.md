# bitdrift demos and utilities

> **[bitdrift](https://bitdrift.io/)** provides mobile observability for iOS and Android apps. Learn more: [Documentation](https://docs.bitdrift.io/) | [SDK Docs](https://docs.bitdrift.io/sdk/quickstart) | [Blog](https://blog.bitdrift.io/)

This repo contains demo apps and utilities showcasing bitdrift's mobile observability features.

## Projects

### [Sankey Demos](sankeydemo/)
Demo apps for visualizing user journey flows with bitdrift's [User Journey feature](https://bitdrift.io/feature/user-journeys). Includes Android, iOS, and React Native implementations.

### [Symbol Upload](symupload/)
Utility for uploading iOS dSYM files to bitdrift using curl (no CLI required).

## Demo Apps

### Android

#### SDK-Instrumented Apps (`android/bitdrift-sdk-instrumented/`)
- **[sankey-demo-sdk](sankeydemo/android/bitdrift-sdk-instrumented/sankey-demo-sdk/)** - E-commerce flow with bitdrift SDK. Each simulation generates a unique session ID.
- **[sankey-demo-sdk-w-force](sankeydemo/android/bitdrift-sdk-instrumented/sankey-demo-sdk-w-force/)** - SDK version with random force-kill events to test crash handling and `AppExit` tracking.
- **[sankey-demo-sdk-w-quits](sankeydemo/android/bitdrift-sdk-instrumented/sankey-demo-sdk-w-quits/)** - SDK version with probabilistic quit points to simulate app abandonment across different exit types.
- **[support-sankey-demo-sdk](sankeydemo/android/bitdrift-sdk-instrumented/support-sankey-demo-sdk/)** - SDK version with device code generation and support log field toggle for debugging workflows.

#### Baseline Apps (No SDK) (`android/no-sdk/`)
- **[sankey-demo-no-sdk](sankeydemo/android/no-sdk/sankey-demo-no-sdk/)** - Pure Android baseline for performance comparison.
- **[sankey-demo-no-sdk-w-force](sankeydemo/android/no-sdk/sankey-demo-no-sdk-w-force/)** - Baseline with force-kill events.
- **[sankey-demo-no-sdk-w-quits](sankeydemo/android/no-sdk/sankey-demo-no-sdk-w-quits/)** - Baseline with quit points.

### iOS

#### SDK-Instrumented Apps (`ios/bitdrift-sdk-instrumented/`)
- **[sankey-demo-sdk](sankeydemo/ios/bitdrift-sdk-instrumented/sankey-demo-sdk/)** - E-commerce flow with bitdrift SDK. Each simulation generates a unique session ID.
- **[support-sankey-demo-sdk](sankeydemo/ios/bitdrift-sdk-instrumented/support-sankey-demo-sdk/)** - SDK version with device code generation and support log field toggle.

#### Baseline Apps (No SDK) (`ios/no-sdk/`)
- **[sankey-demo-no-sdk](sankeydemo/ios/no-sdk/sankey-demo-no-sdk/)** - Pure SwiftUI baseline for performance comparison.
- **[support-sankey-demo-no-sdk](sankeydemo/ios/no-sdk/support-sankey-demo-no-sdk/)** - Baseline without support features.

### React Native

- **[sankey-demo-no-sdk](sankeydemo/react-native/sankey-demo-no-sdk/)** - React Native baseline implementation.

## Getting Started

Each project has its own README with detailed setup instructions:

```bash
# Android SDK example
open sankeydemo/android/bitdrift-sdk-instrumented/sankey-demo-sdk/README.md

# iOS SDK example
open sankeydemo/ios/bitdrift-sdk-instrumented/sankey-demo-sdk/README.md

# React Native example
open sankeydemo/react-native/sankey-demo-no-sdk/README.md

# Symbol upload utility
open symupload/README.md
```

## Key Features

- **User Journey Tracking:** Visualize user flows with Sankey diagrams
- **Session Management:** Each simulated journey creates a unique session ID
- **Crash Testing:** Force-kill and quit variants for testing recovery scenarios
- **Support Workflows:** Device code generation and support log toggles
- **Performance Baselines:** No-SDK versions for comparing overhead
