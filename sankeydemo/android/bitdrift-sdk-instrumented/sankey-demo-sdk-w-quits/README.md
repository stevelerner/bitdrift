# Sankey Demo (with bitdrift SDK + Random Force Quits)

An Android demo app showcasing [bitdrift's](https://bitdrift.io/) mobile observability capabilities with **random force quit behavior** to test [crash reporting](https://bitdrift.io/use-cases/crash-reporting), `AppExit` event detection, and automatic restart scenarios.

> Learn more: [Documentation](https://docs.bitdrift.io/) | [Issues & Crashes](https://docs.bitdrift.io/sdk/features-fatal-issues) | [Blog](https://blog.bitdrift.io/)

## Features

- **bitdrift SDK** for screen view tracking and analytics
- **7-step e-commerce flow** with multiple branching paths at each decision point
- **Infinite simulation** mode for continuous journey testing
- **Random force quit** at configurable steps during simulation
- **Automatic restart** and simulation resume after force quit
- **Centralized logging** for all navigation and crash events

## User Journey Flow

```
Step 1: Welcome
    ↓
Step 2: Browse / Search
    ↓
Step 3: Featured Products / Categories
    ↓
Step 4: Product Detail / Reviews
    ↓
Step 5: Cart / Wishlist
    ↓
Step 6a: Guest Checkout / Sign In
    ↓
Step 6b: Card / Apple Pay / PayPal
    ↓
Step 7: Confirmation
```

## bitdrift SDK Integration

All screen views are automatically logged to bitdrift via `ScreenContainer`:

```kotlin
// In Components.kt
DisposableEffect(screenName) {
    ScreenLogger.logScreenView(screenName)  // → BitdriftLogger.logScreenView()
    onDispose { }
}
```

**Tracked screens:** Welcome, Browse, Search, Featured, Categories, ProductDetail, Reviews, Cart, Wishlist, CheckoutGuest, CheckoutSignIn, PaymentCard, PaymentApplePay, PaymentPayPal, Confirmation

## Random Force Quit Behavior

During infinite simulation, the app may randomly force quit at any step (except the final Confirmation step). The app **cycles through different exit types** to test various `AppExit` scenarios:

| Exit Type | Method | Expected `_app_exit_reason` |
|-----------|--------|----------------------------|
| `CRASH` | Throws `RuntimeException` | `CRASH` |
| `EXIT_SELF` | Calls `exitProcess(0)` | `EXIT_SELF` |
| `ANR` | Blocks main thread for 30s | `ANR` |

When triggered:

1. Simulation state is saved to SharedPreferences
2. The current step and exit type are logged (`force_quit_triggered`)
3. The appropriate exit is triggered (crash, exit, or ANR)
4. The external `restart_monitor.sh` script detects the quit and restarts the app
5. On restart, the app automatically resumes infinite simulation
6. On next launch, bitdrift SDK logs `AppExit` event with the previous session's exit reason

### Running with Auto-Restart

Due to Android 12+ background activity launch restrictions, restart is handled externally via adb.

**Terminal 1** - Run the restart monitor:
```bash
./restart_monitor.sh
```

**Terminal 2 / Android Studio** - Run the app and tap "∞ Infinite Sim"

When a force quit occurs, the monitor script automatically restarts the app, which then auto-resumes infinite simulation. This creates a continuous loop of simulations with random crashes.

### Configuration

Force quit probability is **step-weighted** to favor later steps:

| Steps | Screens | Probability |
|-------|---------|-------------|
| 2-4 | Browse, Featured, Product | 3% |
| 5-7 | Cart, Checkout, Payment | 10% |

This ensures most crashes occur during checkout/payment flows rather than early browsing.

```kotlin
// Enable/disable force quits
AppRestartManager.isEnabled = true
```

### Log Events

| Event | Description |
|-------|-------------|
| `force_quit_triggered` | Logged when a random force quit occurs (includes step name and exit_type) |
| `app_open` | Logged when app starts (including after restart) |
| `simulation_start` | Logged when simulation begins |
| `simulation_cancelled` | Logged if force quit interrupts simulation |

### AppExit Events (Bitdrift)

On each app launch, the bitdrift SDK automatically logs an `AppExit` event with details from the previous session:

| Field | Description |
|-------|-------------|
| `_app_exit_reason` | CRASH, EXIT_SELF, ANR, USER_REQUESTED, etc. |
| `_app_exit_source` | ApplicationExitInfo (previous session) |
| `_app_exit_importance` | FOREGROUND, CACHED, etc. |
| `_app_exit_description` | System-provided description |

## Project Structure

```
app/src/main/java/com/example/sankey_demo/
├── MainActivity.kt          # Entry point, NavHost setup
├── Screen.kt                # Navigation route definitions
├── Screens.kt               # All composable screens
├── SimulationManager.kt     # Automated journey simulation
├── AppRestartManager.kt     # Force quit + restart logic (cycles exit types)
├── ExitTrigger.kt           # ExitType enum (CRASH, EXIT_SELF, ANR)
├── SankeyDemoApp.kt         # Application class + bitdrift init
├── AppLifecycleCallbacks.kt # Lifecycle event tracking
├── ScreenLogger.kt          # Centralized logging + bitdrift
└── Components.kt            # Reusable UI components
```

