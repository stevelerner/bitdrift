# Support Sankey Demo (No SDK)

A demo iOS application demonstrating a simulated e-commerce flow **without any SDK instrumentation**. This serves as a baseline comparison app for testing mobile observability and user journey tracking.

This app uses only basic console logging via `print()` statements.

## Overview

This is the **no-SDK baseline version** of the Support Sankey Demo app. It contains the same user journey flow as the SDK-instrumented version, but with no external dependencies or observability frameworks.

### Purpose

- **Baseline comparison** for performance testing
- **Reference implementation** showing user journey flows without SDK overhead
- **Educational example** of a multi-screen navigation flow

## Implementation Details

### Screen Logger

The app uses a simple `ScreenLogger` wrapper class (defined in `ContentView.swift`) that only prints to console:

```swift
class ScreenLogger {
    static let shared = ScreenLogger()

    func logScreenView(_ screenName: String) {
        print("_screen_name: \(screenName)")
    }

    func logInfo(_ message: String, fields: [String: String] = [:]) {
        // Prints in format: [INFO] message | key=value
    }
}
```

### Logged Events

#### App Lifecycle Events

| Event | Trigger |
|-------|---------|
| `app_did_finish_launching` | App startup |
| `app_open` | App enters foreground |
| `app_close` | App enters background |
| `app_will_terminate` | App termination |
| `memory_pressure` | Memory warning received |
| `low_power_mode` | Power state changes |

#### User Journey Screen Views

The app tracks navigation through an e-commerce flow by printing screen names:

| Step | Screen Name | Description |
|------|-------------|-------------|
| 1 | `Welcome` | App entry point |
| 2 | `Browse` | Browse products |
| 2 | `Search` | Search products |
| 3 | `Featured` | Featured products |
| 3 | `Categories` | Product categories |
| 4 | `ProductDetail` | Product details |
| 4 | `Reviews` | Customer reviews |
| 5 | `Cart` | Shopping cart |
| 5 | `Wishlist` | Saved items |
| 6a | `CheckoutGuest` | Guest checkout |
| 6a | `CheckoutSignIn` | Member checkout |
| 6b | `PaymentCard` | Credit card payment |
| 6b | `PaymentApplePay` | Apple Pay payment |
| 6b | `PaymentPayPal` | PayPal payment |
| 7 | `Confirmation` | Order confirmation |

#### Simulation Events

The app includes a simulation mode for generating test data:

| Event | Description |
|-------|-------------|
| `simulation_start` | Fixed simulation begins |
| `simulation_end` | Simulation completes |
| `simulation_cancelled` | User cancels |
| `infinite_simulation_start` | Infinite mode begins |
| `infinite_simulation_end` | Infinite mode ends |

### Dependencies

**None** - This is a pure SwiftUI app with no external dependencies.

## Running the App

1. Open `support-sankey-demo.xcodeproj` in Xcode
2. Select a simulator or device
3. Build and run

All logs will appear in the Xcode console.
