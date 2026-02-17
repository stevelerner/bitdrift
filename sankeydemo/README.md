# Sankey Demo

A cross-platform demo suite that showcases bitdrift's mobile observability through a simulated e-commerce flow. Use these apps to test user journey visualization, screen view tracking, and workflow-based debugging across Android, iOS, and React Native.

- Learn more: https://bitdrift.io/
- Docs: https://docs.bitdrift.io/
- User journeys: https://bitdrift.io/feature/user-journeys

## Screenshots

### Bitdrift Workflow Configuration

![Bitdrift Workflow](android/bitdrift-sdk-instrumented/sankey-demo-sdk/images/workflow.png)

### Resulting Sankey Diagram

![User Journey Sankey Diagram](android/bitdrift-sdk-instrumented/sankey-demo-sdk/images/userjourney-android.png)

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

- Android (SDK): android/bitdrift-sdk-instrumented/sankey-demo-sdk/
- iOS (SDK): ios/bitdrift-sdk-instrumented/sankey-demo-sdk/
- React Native (No-SDK): react-native/sankey-demo-no-sdk/

Each platform folder contains its own README with setup and run instructions.

## Concept Summary

The demo logs screen views for a fixed set of screens. These events are grouped into user sessions and rendered in the bitdrift dashboard as a Sankey diagram that highlights the most common paths and drop-offs.

If you want a quick walkthrough of the Android implementation details, see:
- android/bitdrift-sdk-instrumented/sankey-demo-sdk/README.md
