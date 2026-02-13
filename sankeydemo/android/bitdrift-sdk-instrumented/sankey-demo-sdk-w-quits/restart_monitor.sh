#!/bin/bash
# Monitor for app death and restart the app
# Handles different exit types: CRASH, EXIT_SELF, ANR
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
echo "Monitoring for app death... (Ctrl+C to stop)"
echo ""

# Function to check if app is running
is_app_running() {
    "$ADB" shell pidof "$PACKAGE" > /dev/null 2>&1
}

# Function to restart the app
restart_app() {
    echo ">>> Restarting app..."
    "$ADB" shell am start -n "$PACKAGE/$ACTIVITY" --activity-clear-task
    echo ">>> App restarted"
    echo ""
}

# Function to wait for app to die
wait_for_death() {
    local max_wait=$1
    local waited=0
    while is_app_running && [ $waited -lt $max_wait ]; do
        sleep 1
        waited=$((waited + 1))
        echo "    Waiting for app to die... ($waited/$max_wait)"
    done
    return $(is_app_running && echo 1 || echo 0)
}

"$ADB" logcat -c  # Clear logcat buffer

"$ADB" logcat | while read -r line; do
    if echo "$line" | grep -q "force_quit_triggered"; then
        # Extract exit type from log
        if echo "$line" | grep -q "exit_type.*CRASH"; then
            echo ">>> CRASH detected"
            sleep 2  # Wait for crash to complete
            if ! is_app_running; then
                restart_app
            fi
        elif echo "$line" | grep -q "exit_type.*EXIT_SELF"; then
            echo ">>> EXIT_SELF detected"
            sleep 2  # Wait for exit to complete
            if ! is_app_running; then
                restart_app
            fi
        elif echo "$line" | grep -q "exit_type.*ANR"; then
            echo ">>> ANR detected - waiting for system to kill app (up to 45s)..."
            wait_for_death 45
            if ! is_app_running; then
                restart_app
            else
                echo ">>> App still running, forcing stop..."
                "$ADB" shell am force-stop "$PACKAGE"
                sleep 1
                restart_app
            fi
        else
            # Fallback for unknown exit type
            echo ">>> Force quit detected (unknown type)"
            sleep 2
            if ! is_app_running; then
                restart_app
            fi
        fi
    fi
done
