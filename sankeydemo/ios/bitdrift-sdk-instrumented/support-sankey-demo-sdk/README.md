# Support Sankey Demo

A demo iOS application showcasing [bitdrift's](https://bitdrift.io/) mobile observability capabilities for support workflows. Demonstrates [user journey visualization](https://bitdrift.io/feature/user-journeys), device linking via temporary device codes, and session-level debugging.

> Learn more: [Documentation](https://docs.bitdrift.io/) | [SDK Quickstart](https://docs.bitdrift.io/sdk/quickstart) | [Blog](https://blog.bitdrift.io/)

## Bitdrift SDK Integration

Follow the [SDK Quickstart](https://docs.bitdrift.io/sdk/quickstart#ios) for complete setup instructions.

### SDK Initialization

The bitdrift Capture SDK is initialized in `support_sankey_demoApp.swift` at app startup:

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
Logger.createTemporaryDeviceCode { result in
    switch result {
    case .success(let code):
        print("Device code: \(code)")
    case .failure(let error):
        print("Failed to create device code: \(error)")
    }
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
| `Logger.addField(withKey:value:)` | Adds a global field attached to every log |
| `Logger.createTemporaryDeviceCode(completion:)` | Generates a temporary device code for dashboard linking |

### App Wrapper Class

This demo app uses a custom `ScreenLogger` singleton wrapper (defined in `ContentView.swift`) that calls the bitdrift SDK methods internally:

```swift
class ScreenLogger {
    static let shared = ScreenLogger()

    func logScreenView(_ screenName: String) {
        Logger.logScreenView(screenName: screenName)  // bitdrift SDK
        print("_screen_name: \(screenName)")          // also prints to console
    }

    func logInfo(_ message: String, fields: [String: String] = [:]) {
        Logger.logInfo(message, fields: fields)       // bitdrift SDK
        print("INFO: \(message) - \(fields)")
    }
    // ... similar for logDebug, logWarning
}
```

This wrapper provides a centralized place to add console output alongside bitdrift logging.

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

### Support Features

The Welcome screen includes two support buttons for live debugging with bitdrift:

#### Support Code

Generates a temporary device code via `Logger.createTemporaryDeviceCode()`. The code is valid for ~24 hours and can be shared with support to stream logs from the device in real time:

```bash
bd tail devicecode:<code>
```

#### Support Log

Toggles a global log field `supportlog` between `"true"` and `"false"` using `Logger.addField(withKey:value:)`. This field is attached to every log emitted by the SDK, allowing bitdrift workflows to detect support sessions and trigger enhanced recording (e.g., higher fidelity captures, longer retention).

The field is initialized to `"false"` at app startup. When the user taps the button it flips to `"true"`, and tapping again resets it to `"false"`.

```swift
// Toggling the field
Logger.addField(withKey: "supportlog", value: "true")  // enable
Logger.addField(withKey: "supportlog", value: "false") // disable
```

### Dependencies

The project uses the [capture-ios](https://github.com/bitdriftlabs/capture-ios) SDK via Swift Package Manager.

### Usage Example

```swift
// Log a screen view
ScreenLogger.shared.logScreenView("ProductDetail")

// Log with custom fields
ScreenLogger.shared.logInfo(
    "simulation_start",
    fields: ["total_runs": "100"]
)
```
