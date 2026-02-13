# ShopDemo (React Native) - No SDK

A React Native e-commerce shopping journey simulator app that demonstrates multiple branching paths through a typical retail customer journey.

## Prerequisites

### Required Software

- **Node.js** 18 or later
- **Xcode** 14.0 or later (with iOS Simulator)

### Install Watchman (Required)

Watchman is required for the Metro bundler to watch file changes without hitting system limits:

```bash
brew install watchman
```

### Install CocoaPods

CocoaPods is required to install iOS dependencies. Install via Homebrew (recommended):

```bash
brew install cocoapods
```

Alternatively, if you have a recent Ruby version (3.0+):
```bash
sudo gem install cocoapods
```

## Build Instructions for Xcode

### 1. Install JavaScript Dependencies

```bash
cd sankeydemo/react-native/sankey-demo-no-sdk
npm install
```

### 2. Install iOS Dependencies (CocoaPods)

```bash
cd ios
pod install
cd ..
```

You may see warnings about UUIDs or script phases - these are normal and can be ignored.

### 3. Configure Node Path for Xcode

Create a local environment file so Xcode can find Node:

```bash
cd ios
echo "export NODE_BINARY=$(which node)" > .xcode.env.local
cd ..
```

### 4. Start Metro Bundler

In a terminal, start the Metro bundler (keep this running):

```bash
npm start
```

### 5. Open in Xcode

Open the **workspace** file (not the .xcodeproj):

```bash
open ios/SankeyDemoNoSdk.xcworkspace
```

### 6. Configure Signing

1. In Xcode, select the **SankeyDemoNoSdk** target in the project navigator
2. Go to **Signing & Capabilities** tab
3. Select your **Team** from the dropdown
4. Xcode will automatically manage signing

### 7. Build and Run from Xcode

1. Select a simulator from the device dropdown (top left, e.g., "iPhone 15 Pro")
2. Press **Cmd + R** or click the **Play** button
3. Wait for the build to complete and the app to launch in the simulator

**Important:** Metro must be running before you build, otherwise you'll see "No bundle URL present" error.

## Alternative: Run from Terminal

If you prefer using the command line instead of Xcode:

```bash
# Start Metro in one terminal
npm start

# In another terminal, build and run
npm run ios

# Or specify a simulator
npm run ios -- --simulator="iPhone 15 Pro"
```

## Cleanup Before Git

Run the cleanup script to remove build artifacts before committing:

```bash
./cleanup.sh
```

This removes:
- `node_modules/` - JavaScript dependencies
- `ios/Pods/` - CocoaPods dependencies
- `ios/Podfile.lock` - CocoaPods lock file
- `ios/build/` - Xcode build output
- `ios/.xcode.env.local` - Local Node path config
- Xcode user data and caches
- `.DS_Store` files

### Rebuilding After Clone

After cloning or running cleanup:

```bash
npm install
cd ios && pod install && cd ..
echo "export NODE_BINARY=$(which node)" > ios/.xcode.env.local
npm start
# Then open Xcode and build
```

## Project Structure

```
sankey-demo-no-sdk/
├── App.tsx                 # Main app component with navigation
├── src/
│   ├── components/         # Reusable UI components
│   │   ├── Buttons.tsx
│   │   ├── ScreenContainer.tsx
│   │   ├── SimulationOverlay.tsx
│   │   └── StepIndicator.tsx
│   ├── context/
│   │   └── SimulationContext.tsx  # Simulation state management
│   ├── navigation/
│   │   └── types.ts        # TypeScript navigation types
│   ├── screens/            # All 15 app screens
│   └── utils/
│       ├── colors.ts       # Color definitions
│       └── logger.ts       # Screen logging utility
└── ios/                    # iOS native code
```

## Features

- **7-step shopping journey** with multiple branching paths
- **64 unique journey combinations** (2^6 decision points)
- **Automated simulation** - run 10, 100, or infinite journeys
- **Real-time progress tracking** during simulations
- **Screen-specific colors** for visual distinction

## Journey Flow

```
Welcome → Browse/Search → Featured/Categories → ProductDetail/Reviews
    → Cart/Wishlist → GuestCheckout/SignInCheckout → Payment → Confirmation
```

## Troubleshooting

### "EMFILE: too many open files" error

Install Watchman:
```bash
brew install watchman
```

### Pod install fails with Ruby error

Use Homebrew to install CocoaPods instead:
```bash
brew install cocoapods
```

### Pod install fails with other errors

```bash
cd ios
pod deintegrate
pod cache clean --all
pod install
```

### "Command PhaseScriptExecution failed" error

Xcode can't find Node. Create the local env file:
```bash
cd ios
echo "export NODE_BINARY=$(which node)" > .xcode.env.local
```
Then clean (Cmd+Shift+K) and rebuild (Cmd+R).

### "No bundle URL present" error

Metro bundler isn't running. Start it:
```bash
npm start
```
Then relaunch the app in the simulator.

### Build errors after updating dependencies

```bash
rm -rf node_modules
rm -rf ios/Pods
rm ios/Podfile.lock
npm install
cd ios && pod install
```

### Metro bundler issues

```bash
npm start -- --reset-cache
```

### Scheme not found error

Open Xcode, go to **Product > Scheme > Manage Schemes**, and click "Autocreate Schemes Now".
