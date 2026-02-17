# Sankey Demo (with Swipe-Up Force Quit)

An Android demo app showcasing [bitdrift's](https://bitdrift.io/) mobile observability capabilities with **swipe-up force quit behavior** that produces `USER_REQUESTED` app exit reasons. Uses a companion "Killer" app with an Accessibility Service to simulate user-initiated app dismissal from the recent apps screen.

> Learn more: [Documentation](https://docs.bitdrift.io/) | [Issues & Crashes](https://docs.bitdrift.io/sdk/features-fatal-issues) | [Blog](https://blog.bitdrift.io/)

## Features

- **7-step e-commerce flow** with multiple branching paths at each decision point
- **Infinite automated simulation** with random force quits
- **Swipe-up force quit** via companion Killer app (produces `USER_REQUESTED` exit reason)
- **Automatic restart** and resume of simulation after force quit
- **Centralized logging** for all navigation and crash events

## Architecture

This project contains two apps:

| Module | Package | Description |
|--------|---------|-------------|
| `app` | `com.example.sankey_demo` | Main demo app with e-commerce simulation |
| `killer` | `com.example.appkiller` | Companion app with Accessibility Service for swipe-to-kill |

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

## Swipe-Up Force Quit Behavior

During simulation, the app may randomly trigger a force quit at any step (except the final Confirmation step). Unlike `Process.killProcess()` which produces `EXIT_SELF`, this method produces `USER_REQUESTED` exit reason by having the Killer app swipe away the demo app from recents.

### How It Works

1. Demo app launches and **automatically starts infinite simulation**
2. At each step, the app may randomly trigger a force quit (based on probability)
3. Demo app sends broadcast to Killer app: `com.example.appkiller.ACTION_KILL_APP`
4. Killer app receives broadcast via `KillRequestReceiver` and:
   - Opens the Recent Apps screen via `GLOBAL_ACTION_RECENTS`
   - Waits 800ms for recents to open
   - Performs a swipe-up gesture to dismiss the demo app
   - Navigates to home screen
   - Waits 1.5 seconds for Android to fully terminate the process and record `ApplicationExitInfo`
   - Relaunches the demo app
5. Android system kills the demo app with `USER_REQUESTED` exit reason
6. On next launch, bitdrift SDK reads `ApplicationExitInfo` and logs the `AppExit` event
7. Demo app restarts and automatically begins simulation again (infinite loop)

**Note:** The 1.5 second delay before restart is important - it allows Android to fully terminate the process and record `ApplicationExitInfo`, which is required for the SDK to log `AppExit` (not just `AppStop`).

### Setup

1. **Enable the Killer app's Accessibility Service:**
   - Open the "App Killer" app
   - Tap "Enable Service"
   - Find "App Killer" in the accessibility settings
   - Enable the service

2. **Run the demo app:**
   - Open "Sankey Demo" app
   - Simulation starts automatically
   - Force quits will happen randomly, producing `USER_REQUESTED` exit events
   - The app will auto-restart and continue simulating indefinitely

### Force Quit Probability

Probability is **step-weighted** to simulate realistic user drop-off patterns:

| Steps | Screens | Probability |
|-------|---------|-------------|
| 1-4 | Welcome, Browse, Featured, Product | 3% |
| 5-7 | Cart, Checkout, Payment | 10% |

This ensures more force quits occur during high-value checkout/payment flows.

### Configuration

```kotlin
// Enable/disable force quits entirely
AppRestartManager.isEnabled = true
```

### Log Events

**Note:** Each simulated journey generates a new session ID via `Logger.startNewSession()`, allowing you to track individual user journeys separately in the bitdrift dashboard.

| Event | Description |
|-------|-------------|
| `force_quit_triggered` | Logged when swipe-up force quit is triggered (includes step name, exit_type=USER_REQUESTED) |
| `AppExit` | Logged by bitdrift SDK on next app launch with previous exit info |
| `app_open` | Logged when app starts (including after restart) |
| `simulation_start` | Logged when simulation begins |
| `simulation_cancelled` | Logged if force quit interrupts simulation |

### AppExit Event Fields

When the app restarts after a swipe-to-kill, bitdrift's SDK logs an `AppExit` event with these fields:

| Field | Value | Description |
|-------|-------|-------------|
| `_app_exit_reason` | `USER_REQUESTED` | Indicates user swiped app from recents |
| `_app_exit_importance` | `CACHED` | App was backgrounded when killed |
| `_app_exit_description` | `[REMOVE TASK] remove task` | Android's description for task removal |
| `_app_exit_source` | `ApplicationExitInfo` | Data source (Android API) |
| `_app_exit_status` | `0` | Exit status code |
| `_app_exit_pss` | `0` | Proportional set size (memory) |
| `_app_exit_rss` | `0` | Resident set size (memory) |

**Note:** The `_app_exit_importance` will be `CACHED` (not `FOREGROUND`) because the app is backgrounded when the Killer app opens the recents screen before swiping.

## Project Structure

```
├── app/                              # Main demo app
│   └── src/main/java/com/example/sankey_demo/
│       ├── MainActivity.kt           # Entry point, NavHost setup
│       ├── Screen.kt                 # Navigation route definitions
│       ├── Screens.kt                # All composable screens
│       ├── SimulationManager.kt      # Automated journey simulation
│       ├── AppRestartManager.kt      # Force quit via broadcast to Killer app
│       ├── SankeyDemoApp.kt          # Application class
│       ├── AppLifecycleCallbacks.kt  # Lifecycle event tracking
│       ├── ScreenLogger.kt           # Centralized logging
│       └── Components.kt             # Reusable UI components
│
└── killer/                           # Companion Killer app
    └── src/main/java/com/example/appkiller/
        ├── MainActivity.kt           # UI for enabling service
        ├── AppKillerService.kt       # Accessibility Service for swipe gestures
        └── KillRequestReceiver.kt    # Broadcast receiver for kill requests
```

## Debugging

To monitor the Killer app's kill sequence:

```bash
~/Library/Android/sdk/platform-tools/adb logcat -s AppKillerService
```

Expected log output for a successful kill/restart cycle:
```
Starting kill sequence for: com.example.sankey_demo
Opened recents: true
Performing swipe up gesture
Gesture dispatched: true
Swipe gesture completed
finishKillSequence called, will restart: com.example.sankey_demo
Going home...
Attempting restart of: com.example.sankey_demo
App restarted successfully via launch intent
Kill sequence complete, ready for next request
```

## Why USER_REQUESTED?

The `USER_REQUESTED` exit reason in Android's `ApplicationExitInfo` indicates the user explicitly closed the app (e.g., by swiping it away from recents or force-stopping from Settings). This is distinct from:

- `EXIT_SELF` - App called `System.exit()` or `Process.killProcess()`
- `CRASH` - App crashed due to unhandled exception
- `ANR` - App was killed due to Application Not Responding

Testing `USER_REQUESTED` exits helps validate that your app correctly handles and reports this common real-world scenario.
