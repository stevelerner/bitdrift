package com.example.sankey_demo

import android.content.Context
import android.content.Intent
import android.util.Log
import kotlin.random.Random

/**
 * Manages random force quits with automatic app restart.
 * Uses a companion "Killer" app with an Accessibility Service to swipe-dismiss
 * the app from recents, resulting in USER_REQUESTED exit reason.
 */
object AppRestartManager {

    private const val TAG = "AppRestartManager"

    // Killer app broadcast action
    private const val ACTION_KILL_APP = "com.example.appkiller.ACTION_KILL_APP"
    private const val EXTRA_PACKAGE_NAME = "package_name"

    private var applicationContext: Context? = null

    // Swipe-up force quit probability (produces USER_REQUESTED exit reason)
    // Steps 1-4: early steps, low probability
    private const val SWIPE_EARLY_STEP_PROBABILITY = 0.03f  // 3%
    // Steps 5-7: late steps, higher probability
    private const val SWIPE_LATE_STEP_PROBABILITY = 0.10f   // 10%

    // Enable/disable random force quits
    var isEnabled: Boolean = true

    /**
     * Initialize with application context. Must be called in Application.onCreate()
     */
    fun initialize(context: Context) {
        applicationContext = context.applicationContext
        Log.d(TAG, "AppRestartManager initialized (swipe early=${(SWIPE_EARLY_STEP_PROBABILITY * 100).toInt()}%, swipe late=${(SWIPE_LATE_STEP_PROBABILITY * 100).toInt()}%)")
    }

    /**
     * Get the probability for a given step number.
     * Steps 1-4 (early): low probability (3%)
     * Steps 5-7 (late): high probability (10%)
     */
    private fun getProbabilityForStep(step: Int): Float {
        return if (step >= 5) SWIPE_LATE_STEP_PROBABILITY else SWIPE_EARLY_STEP_PROBABILITY
    }

    /**
     * Check if a random force quit should occur based on step-weighted probability.
     * @param step The current step number (2-7)
     * @return true if force quit should happen
     */
    fun shouldForceQuit(step: Int): Boolean {
        if (!isEnabled) return false
        val probability = getProbabilityForStep(step)
        return Random.nextFloat() < probability
    }

    /**
     * Maybe trigger a force quit based on step-weighted probability.
     * Later steps (5-7) have much higher chance than early steps (2-4).
     * @param stepName Name of the current step (for logging)
     * @param step The current step number (2-7)
     * @return true if force quit was triggered (app will terminate)
     */
    fun maybeForceQuit(stepName: String, step: Int): Boolean {
        if (!shouldForceQuit(step)) return false

        forceQuitAndRestart(stepName)
        return true
    }

    /**
     * Force quit the app by sending a broadcast to the Killer app.
     * The Killer app will swipe away this app from recents, producing USER_REQUESTED exit reason.
     * @param reason Reason for the force quit (for logging)
     */
    fun forceQuitAndRestart(reason: String = "manual") {
        val context = applicationContext ?: run {
            Log.e(TAG, "Cannot force quit: context not initialized")
            return
        }

        ScreenLogger.logInfo(
            "force_quit_triggered",
            mapOf(
                "reason" to reason,
                "exit_type" to "USER_REQUESTED"
            )
        )
        Log.w(TAG, "Force quit triggered at step: $reason (swipe-up via Killer app)")

        // Small delay to ensure prefs and log are written
        Thread.sleep(100)

        // Send broadcast to the Killer app to swipe us away from recents
        val killIntent = Intent(ACTION_KILL_APP).apply {
            putExtra(EXTRA_PACKAGE_NAME, context.packageName)
            // Set package to ensure it goes to our killer app
            setPackage("com.example.appkiller")
        }
        context.sendBroadcast(killIntent)
        Log.d(TAG, "Kill request sent to Killer app")
    }
}
