package com.example.appkiller

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent

/**
 * Accessibility service that can swipe away apps from the recent apps screen.
 * This simulates a user force-quitting an app, resulting in USER_REQUESTED exit reason.
 */
class AppKillerService : AccessibilityService() {

    companion object {
        private const val TAG = "AppKillerService"
        const val ACTION_KILL_APP = "com.example.appkiller.ACTION_KILL_APP"
        const val EXTRA_PACKAGE_NAME = "package_name"

        var instance: AppKillerService? = null
            private set

        fun isRunning(): Boolean = instance != null
    }

    private val handler = Handler(Looper.getMainLooper())
    private var targetPackage: String? = null
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private var isKillInProgress: Boolean = false
    private var finishSequenceCalled: Boolean = false  // Guard against duplicate calls

    // Note: Kill requests are handled by KillRequestReceiver registered in the manifest.
    // We don't register a duplicate receiver here to avoid double-handling broadcasts.

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this

        // Get screen dimensions
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bounds = windowManager.currentWindowMetrics.bounds
            screenWidth = bounds.width()
            screenHeight = bounds.height()
        } else {
            val metrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(metrics)
            screenWidth = metrics.widthPixels
            screenHeight = metrics.heightPixels
        }

        Log.d(TAG, "Service connected. Screen: ${screenWidth}x${screenHeight}")
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
        Log.d(TAG, "Service destroyed")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // We don't need to process accessibility events for our purpose
    }

    override fun onInterrupt() {
        Log.d(TAG, "Service interrupted")
    }

    /**
     * Kill an app by opening recent apps and swiping it away.
     */
    fun killApp(packageName: String) {
        if (isKillInProgress) {
            Log.w(TAG, "Kill already in progress, ignoring: $packageName")
            return
        }
        isKillInProgress = true
        finishSequenceCalled = false  // Reset guard
        targetPackage = packageName
        Log.d(TAG, "Starting kill sequence for: $packageName")

        // Step 1: Open recent apps
        val opened = performGlobalAction(GLOBAL_ACTION_RECENTS)
        Log.d(TAG, "Opened recents: $opened")

        // Step 2: Wait for recents to fully open, then swipe up to dismiss the app
        // Using longer delay (800ms) for more reliability
        handler.postDelayed({
            performSwipeUp()
        }, 800)
    }

    /**
     * Perform a swipe up gesture to dismiss the top app from recents.
     * The demo app should be the most recent one since it just requested the kill.
     */
    private fun performSwipeUp() {
        Log.d(TAG, "Performing swipe up gesture")

        // Swipe from center of screen upward
        val startX = screenWidth / 2f
        val startY = screenHeight * 0.6f  // Start from lower-middle
        val endY = screenHeight * 0.1f    // End near top

        val path = Path().apply {
            moveTo(startX, startY)
            lineTo(startX, endY)
        }

        val gesture = GestureDescription.Builder()
            .addStroke(GestureDescription.StrokeDescription(path, 0, 250))
            .build()

        val dispatched = dispatchGesture(gesture, object : GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription?) {
                Log.d(TAG, "Swipe gesture completed")
                finishKillSequence()
            }

            override fun onCancelled(gestureDescription: GestureDescription?) {
                Log.e(TAG, "Swipe gesture cancelled - will still try to restart app")
                finishKillSequence()
            }
        }, null)

        Log.d(TAG, "Gesture dispatched: $dispatched")

        // Fallback: if dispatch fails, still try to finish the sequence
        if (!dispatched) {
            Log.e(TAG, "Gesture dispatch failed - using fallback")
            handler.postDelayed({
                finishKillSequence()
            }, 500)
        }
    }

    /**
     * Complete the kill sequence: go home and restart the app.
     * Uses moderate delays to ensure Android terminates the process
     * and records ApplicationExitInfo (needed for AppExit vs AppStop).
     */
    private fun finishKillSequence() {
        // Guard against duplicate calls (gesture callback + fallback can both trigger this)
        if (finishSequenceCalled) {
            Log.w(TAG, "finishKillSequence already called, ignoring duplicate")
            return
        }
        finishSequenceCalled = true

        val packageToRestart = targetPackage  // Capture now in case it changes
        Log.d(TAG, "finishKillSequence called, will restart: $packageToRestart")

        // Go back to home after dismissing
        handler.postDelayed({
            Log.d(TAG, "Going home...")
            performGlobalAction(GLOBAL_ACTION_HOME)

            // Wait for process to fully terminate and ApplicationExitInfo to be recorded
            handler.postDelayed({
                Log.d(TAG, "Attempting restart of: $packageToRestart")
                restartApp(packageToRestart)
                // Reset flag after restart attempt
                handler.postDelayed({
                    isKillInProgress = false
                    Log.d(TAG, "Kill sequence complete, ready for next request")
                }, 500)
            }, 1500)  // 1.5 seconds - balance between termination time and responsiveness
        }, 400)
    }

    /**
     * Restart the target app after it was swiped away.
     */
    private fun restartApp(packageName: String?) {
        if (packageName == null) {
            Log.e(TAG, "Cannot restart: no package name")
            return
        }

        Log.d(TAG, "Restarting app: $packageName")
        try {
            val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(launchIntent)
                Log.d(TAG, "App restarted successfully via launch intent")
            } else {
                Log.e(TAG, "Could not get launch intent, trying explicit intent")
                // Fallback: try explicit intent to main activity
                val fallbackIntent = Intent().apply {
                    setClassName(packageName, "$packageName.MainActivity")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
                startActivity(fallbackIntent)
                Log.d(TAG, "App restarted via explicit intent")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to restart app: ${e.message}", e)
        }
    }
}
