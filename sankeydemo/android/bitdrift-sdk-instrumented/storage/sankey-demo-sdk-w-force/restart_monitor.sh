#!/bin/bash
# Monitor logcat for force quit events and restart the app
# Run this script while testing to auto-restart after force quits

PACKAGE="com.example.sankey_demo"
ACTIVITY="com.example.sankey_demo.MainActivity"

# Find adb - check common locations
if [ -n "$ANDROID_HOME" ] && [ -f "$ANDROID_HOME/platform-tools/adb" ]; then
    ADB="$ANDROID_HOME/platform-tools/adb"
elif [ -f "$HOME/Library/Android/sdk/platform-tools/adb" ]; then
    ADB="$HOME/Library/Android/sdk/platform-tools/adb"
elif command -v adb &> /dev/null; then
    ADB="adb"
else
    echo "Error: adb not found. Set ANDROID_HOME or add adb to PATH"
    exit 1
fi

echo "Using adb: $ADB"
echo "Monitoring for force quits... (Ctrl+C to stop)"

"$ADB" logcat -c  # Clear logcat buffer

"$ADB" logcat | while read -r line; do
    if echo "$line" | grep -q "force_quit_triggered"; then
        echo ">>> Force quit detected, restarting in 1 second..."
        sleep 1
        "$ADB" shell am start -n "$PACKAGE/$ACTIVITY" --activity-clear-task
        echo ">>> App restarted"
    fi
done
