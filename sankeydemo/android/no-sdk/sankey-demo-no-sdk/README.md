# Sankey Demo (No SDK)

A demo Android application that simulates e-commerce user journeys through a 7-step flow for testing and visualization.

**This version does not include the bitdrift SDK** - all logging goes to Android logcat only.

## Features

- **7-step e-commerce flow** with multiple branching paths at each decision point
- **Automated simulation** (10, 100, or infinite runs)
- **Logcat logging** for all navigation and events

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

## Logged Events (Logcat)

All events are logged to Android logcat with tag `ScreenLogger`:

| Event | Description |
|-------|-------------|
| `_screen_name: <name>` | Screen view logged for each navigation |
| `app_open` | App enters foreground |
| `app_close` | App enters background |
| `simulation_start` | Simulation begins |
| `simulation_end` | Simulation completes |

## Project Structure

```
app/src/main/java/com/example/sankey_demo/
├── MainActivity.kt          # Entry point, NavHost setup
├── Screen.kt                # Navigation route definitions
├── Screens.kt               # All composable screens
├── SimulationManager.kt     # Automated journey simulation
├── SankeyDemoApp.kt         # Application class
├── AppLifecycleCallbacks.kt # Lifecycle event tracking
├── ScreenLogger.kt          # Centralized logging (logcat only)
└── Components.kt            # Reusable UI components
```

## Build & Run

1. Open the project in Android Studio
2. Sync Gradle files
3. Run on an emulator or device

## Related Apps

- [sankey-demo-sdk](../../bitdrift-sdk-instrumented/sankey-demo-sdk/) - Same app with bitdrift SDK integration
- [sankey-demo-no-sdk-w-quits](../sankey-demo-no-sdk-w-quits/) - With random force quit behavior
- [sankey-demo-no-sdk-w-force](../sankey-demo-no-sdk-w-force/) - With swipe-up force quit behavior
