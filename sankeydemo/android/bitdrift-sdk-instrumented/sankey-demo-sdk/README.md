# Sankey Demo (Android)

A demo Android application showcasing [bitdrift's](https://bitdrift.io/) mobile observability capabilities through a simulated e-commerce flow. Use this app to test [user journey visualization](https://bitdrift.io/feature/user-journeys), screen view tracking, and workflow-based debugging.

> Learn more about bitdrift mobile observability: [Documentation](https://docs.bitdrift.io/) | [Blog](https://blog.bitdrift.io/)

## Screenshots

### Bitdrift Workflow Configuration

![Bitdrift Workflow](images/workflow.png)

### Resulting Sankey Diagram

![User Journey Sankey Diagram](images/userjourney-android.png)

## What This Demo Does

Each app simulates an e-commerce journey with branching paths (browse, search, product detail, cart, checkout, payment, confirmation). Every screen logs a screen view event so the bitdrift dashboard can visualize the full journey as a Sankey diagram.

## Project Layout

```
android/
    bitdrift-sdk-instrumented/
        sankey-demo-sdk/
        support-sankey-demo-sdk/
    no-sdk/
        sankey-demo-no-sdk/
        sankey-demo-no-sdk-w-force/
        sankey-demo-no-sdk-w-quits/
ios/
    bitdrift-sdk-instrumented/
        sankey-demo-sdk/
        support-sankey-demo-sdk/
    no-sdk/
        sankey-demo-no-sdk/
        support-sankey-demo-no-sdk/
react-native/
    sankey-demo-no-sdk/
```

## App Variants

- SDK instrumented: ready-to-run examples integrated with the bitdrift SDK.
- No-SDK: baseline projects without bitdrift integration for comparison or custom wiring.
- Support apps: auxiliary builds used in the demo flow and testing.

## Where To Start

- Android (SDK): ./
- iOS (SDK): ../../../ios/bitdrift-sdk-instrumented/sankey-demo-sdk/
- React Native (No-SDK): ../../../react-native/sankey-demo-no-sdk/

Each platform folder contains its own README with setup and run instructions.

## Concept Summary

The demo logs screen views for a fixed set of screens. These events are grouped into user sessions and rendered in the bitdrift dashboard as a Sankey diagram that highlights the most common paths and drop-offs.

## Bitdrift SDK Integration

Follow the [SDK Quickstart](https://docs.bitdrift.io/sdk/quickstart) for complete setup instructions.

### 1. Add Dependencies

In `app/build.gradle.kts`, add the SDK dependency (see [Android releases](https://docs.bitdrift.io/sdk/releases-android) for current version):

```kotlin
dependencies {
    // bitdrift SDK
    implementation("io.bitdrift:capture:<current-version>")
}
```

### 2. Add Gradle Plugin (Optional)

For [automatic network instrumentation](https://docs.bitdrift.io/sdk/integrations.html#auto-instrumentation-via-gradle-plugin) and [proguard mapping uploads](https://docs.bitdrift.io/sdk/features-fatal-issues.html#gradle-plugin-android), add the plugin:

```kotlin
plugins {
    id("io.bitdrift.capture-plugin") version "<current-version>"
}
```

### 3. Initialize the SDK

In your `Application` class (`SankeyDemoApp.kt`), initialize the logger at startup:

```kotlin
import io.bitdrift.capture.Capture.Logger
import io.bitdrift.capture.providers.session.SessionStrategy

class SankeyDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // bitdrift
        Logger.start(
            apiKey = "<your-api-key>",
            sessionStrategy = SessionStrategy.Fixed(),
        )
    }
}
```

**Configuration:**
- **Session Strategy:** `SessionStrategy.Fixed()` — maintains consistent session tracking. See [Session Management](https://docs.bitdrift.io/sdk/features-session-management) for other options.
- Get your API key from Company Settings in the [bitdrift dashboard](https://bitdrift.io/login)

## Logging Implementation

### Bitdrift SDK Methods

The Capture SDK provides these logging methods on the `Logger` class:

| Method | Description |
|--------|-------------|
| `Logger.logScreenView(screenName)` | Logs screen view events for user journey tracking |
| `Logger.logInfo(message, fields)` | Info-level log messages |
| `Logger.logDebug(message, fields)` | Debug-level log messages |
| `Logger.logWarning(message, fields)` | Warning-level log messages |

### ScreenLogger Wrapper

This app uses a centralized `ScreenLogger` object (`ScreenLogger.kt`) that wraps the bitdrift SDK:

```kotlin
object ScreenLogger {
    fun logScreenView(screenName: String) {
        // bitdrift
        BitdriftLogger.logScreenView(screenName)

        Log.d(TAG, "_screen_name: $screenName")
    }
}
```

All screens use the `ScreenContainer` composable which automatically logs screen views via `DisposableEffect`:

```kotlin
@Composable
fun ScreenContainer(screenName: String, ...) {
    DisposableEffect(screenName) {
        ScreenLogger.logScreenView(screenName)
        onDispose { }
    }
    // ... UI content
}
```

### User Journey Screen Views

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
| 6 | `CheckoutGuest` | Guest checkout |
| 6 | `CheckoutSignIn` | Member checkout |
| 6 | `PaymentCard` | Credit card payment |
| 6 | `PaymentApplePay` | Apple Pay payment |
| 6 | `PaymentPayPal` | PayPal payment |
| 7 | `Confirmation` | Order confirmation |

### Simulation Mode

The app includes a simulation mode for generating test data. **Each simulated journey generates a new session ID** via `Logger.startNewSession()`, allowing you to track individual user journeys separately in the bitdrift dashboard.

Simulation events are logged via `ScreenLogger.logInfo()` which writes to Android's `Log.d` only (not sent to bitdrift):

| Event | Fields | Description |
|-------|--------|-------------|
| `simulation_start` | `total_runs` | Fixed simulation begins |
| `simulation_end` | `total_runs` | Simulation completes |
| `simulation_cancelled` | `completed_runs`, `total_runs` | User cancels |
| `infinite_simulation_start` | - | Infinite mode begins |
| `infinite_simulation_end` | `total_runs` | Infinite mode ends |

## Project Structure

```
app/src/main/java/com/example/sankey_demo/
├── SankeyDemoApp.kt      # Application class with bitdrift initialization
├── ScreenLogger.kt       # Centralized logging wrapper
├── Screen.kt             # Navigation routes (sealed class)
├── Screens.kt            # All screen composables
├── Components.kt         # Reusable UI components (ScreenContainer)
├── MainActivity.kt       # Main activity with NavHost
├── SimulationManager.kt  # Automated journey simulation
└── AppLifecycleCallbacks.kt # App lifecycle event logging
```

## Requirements

- Android API level 26+ (minSdk)
- Kotlin
- Jetpack Compose
