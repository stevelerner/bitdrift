# Sankey Demo

A demo iOS application showcasing [bitdrift's](https://bitdrift.io/) mobile observability capabilities through a simulated e-commerce flow. Use this app to test [user journey visualization](https://bitdrift.io/feature/user-journeys), screen view tracking, and workflow-based debugging.

> Learn more: [Documentation](https://docs.bitdrift.io/) | [SDK Quickstart](https://docs.bitdrift.io/sdk/quickstart) | [Blog](https://blog.bitdrift.io/)

## Bitdrift SDK Integration

Follow the [SDK Quickstart](https://docs.bitdrift.io/sdk/quickstart#ios) for complete setup instructions.

### SDK Initialization

The bitdrift Capture SDK is initialized in `sankey_demoApp.swift` at app startup:

```swift
import Capture

Logger.start(
    withAPIKey: apiKey,
    sessionStrategy: .fixed()
)?
.enableIntegrations([.urlSession()])
```

**Configuration:**
- **Session Strategy:** `.fixed()` - maintains consistent session tracking
- **URLSession Integration:** Automatically logs HTTP requests

**Device Linking:**
On startup, the app generates a temporary device code for linking to the bitdrift dashboard:

```swift
if let deviceCode = Logger.createTemporaryDeviceCode() {
    print("Device code: \(deviceCode)")
}
```

### Bitdrift SDK Methods

The Capture SDK provides these logging methods on the `Logger` class:

| Method | Description |
|--------|-------------|
| `Logger.logScreenView(screenName:)` | Logs screen view events for user journey tracking |
| `Logger.logInfo(_:fields:)` | Info-level log messages |
| `Logger.logDebug(_:fields:)` | Debug-level log messages |
| `Logger.logWarning(_:fields:)` | Warning-level log messages |
| `Logger.startNewSession()` | Generates a new session ID for tracking separate user sessions |

### Logging Implementation

#### Dual Logging Approach

This app implements a **dual logging strategy**:

1. **bitdrift Cloud Logging** - All events are sent to the bitdrift dashboard for analysis, visualization, and alerting
2. **Console Logging** - Events are also printed to Xcode console for local debugging

#### ScreenLogger Wrapper

The app uses a custom `ScreenLogger` singleton wrapper (defined in `ContentView.swift`) that encapsulates both logging destinations:

```swift
final class ScreenLogger {
    static let shared = ScreenLogger()
    
    private init() {}
    
    // Logs screen views for user journey tracking
    func logScreenView(_ screenName: String) {
        // → Sent to bitdrift dashboard
        Logger.logScreenView(screenName: screenName)
        
        // → Printed to Xcode console
        print("_screen_name: \(screenName)")
    }
    
    // Logs info-level events with optional structured fields
    func logInfo(_ message: String, fields: [String: String] = [:]) {
        // → Sent to bitdrift dashboard with structured fields
        Logger.logInfo(message, fields: fields)
        
        // → Printed to Xcode console
        printLog(level: "INFO", message: message, fields: fields)
    }
    
    // Similar implementations for logDebug() and logWarning()
    // ...
    
    private func printLog(level: String, message: String, fields: [String: String]) {
        var output = "[\(level)] \(message)"
        if !fields.isEmpty {
            let fieldStrings = fields.map { "\($0.key)=\($0.value)" }.sorted()
            output += " | " + fieldStrings.joined(separator: " | ")
        }
        print(output)
    }
}
```

**Benefits of this approach:**
- All events are captured in bitdrift for production analysis
- Local console output available during development
- Single wrapper maintains consistency across the app
- Easy to add additional logging destinations

#### Automatic Screen View Logging

Every screen in the app automatically logs its view using the `ScreenContainer` composable's `onAppear` modifier:

```swift
struct ScreenContainer<Content: View>: View {
    let screenName: String
    // ... other properties
    
    var body: some View {
        VStack {
            // ... UI content
        }
        .onAppear {
            ScreenLogger.shared.logScreenView(screenName)
        }
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

The app tracks navigation through an e-commerce flow. Each screen logs via `logScreenView()`:

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

The app includes a simulation mode for generating test data. **Each simulated journey generates a new session ID** via `Logger.startNewSession()`, allowing you to track individual user journeys separately in the bitdrift dashboard.

| Event | Fields | Description |
|-------|--------|-------------|
| `simulation_start` | `total_runs` | Fixed simulation begins |
| `simulation_end` | `total_runs` | Simulation completes |
| `simulation_cancelled` | `completed_runs`, `total_runs` | User cancels |
| `infinite_simulation_start` | - | Infinite mode begins |
| `infinite_simulation_end` | `total_runs` | Infinite mode ends |

### Dependencies

The project uses the [capture-ios](https://github.com/bitdriftlabs/capture-ios) SDK via Swift Package Manager.

**SDK Version:** 0.22.7

**Frameworks:**
- **Capture** - Main bitdrift SDK for mobile observability
- CocoaLumberjack 3.9.0 - Logging framework dependency
- swift-log 1.9.1 - Swift logging API dependency
- SwiftyBeaver 2.1.1 - Logging platform dependency

### Usage Example

```swift
// Log a screen view
ScreenLogger.shared.logScreenView(screenName: "ProductDetail")

// Log with custom fields
ScreenLogger.shared.logInfo(
    message: "simulation_start",
    fields: ["total_runs": "100"]
)
```
