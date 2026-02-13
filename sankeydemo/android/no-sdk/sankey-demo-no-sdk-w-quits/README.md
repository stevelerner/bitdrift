# Sankey Demo (No SDK, with Random Force Quits)

An Android demo app that simulates e-commerce user journeys with **random force quit behavior** and **automatic restart**. This variant is designed to test app resilience and observe `AppExit` events in crash/termination scenarios.

**This version does not include the bitdrift SDK** - all logging goes to Android logcat only.

## Features

- **7-step e-commerce flow** with multiple branching paths at each decision point
- **Automated simulation** (10, 100, or infinite runs)
- **Random force quit** at any step during simulation
- **Automatic restart** via AlarmManager after force quit
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

## Random Force Quit Behavior

During infinite simulation, the app may randomly force quit at any step (except the final Confirmation step). When triggered:

1. Simulation state is saved to SharedPreferences
2. The current step name is logged (`force_quit_triggered`)
3. The process is killed via `Process.killProcess()`
4. The external `restart_monitor.sh` script detects the quit and restarts the app
5. On restart, the app automatically resumes infinite simulation

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
| `force_quit_triggered` | Logged when a random force quit occurs (includes step name) |
| `app_open` | Logged when app starts (including after restart) |
| `simulation_start` | Logged when simulation begins |
| `simulation_cancelled` | Logged if force quit interrupts simulation |

## Project Structure

```
app/src/main/java/com/example/sankey_demo/
├── MainActivity.kt          # Entry point, NavHost setup
├── Screen.kt                # Navigation route definitions
├── Screens.kt               # All composable screens
├── SimulationManager.kt     # Automated journey simulation
├── AppRestartManager.kt     # Force quit + restart logic
├── SankeyDemoApp.kt         # Application class
├── AppLifecycleCallbacks.kt # Lifecycle event tracking
├── ScreenLogger.kt          # Centralized logging
└── Components.kt            # Reusable UI components
```

