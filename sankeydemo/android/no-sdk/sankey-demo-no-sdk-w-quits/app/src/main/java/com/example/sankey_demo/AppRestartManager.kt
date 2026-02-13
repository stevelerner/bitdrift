package com.example.sankey_demo

import android.content.Context
import android.content.SharedPreferences
import android.os.Process
import android.util.Log
import kotlin.random.Random

/**
 * Manages random force quits with automatic app restart.
 * Used for testing app behavior during unexpected terminations.
 */
object AppRestartManager {

    private const val TAG = "AppRestartManager"
    private const val PREFS_NAME = "restart_manager_prefs"
    private const val KEY_WAS_SIMULATING = "was_simulating"

    private var applicationContext: Context? = null
    private var prefs: SharedPreferences? = null

    // Probability of force quit - weighted by step (later steps more likely)
    // Steps 2-4: early steps, low probability
    private const val EARLY_STEP_PROBABILITY = 0.03f  // 3%
    // Steps 5-7: late steps, higher probability
    private const val LATE_STEP_PROBABILITY = 0.10f   // 10%

    // Enable/disable random force quits
    var isEnabled: Boolean = true

    /**
     * Initialize with application context. Must be called in Application.onCreate()
     */
    fun initialize(context: Context) {
        applicationContext = context.applicationContext
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        Log.d(TAG, "AppRestartManager initialized (early=${(EARLY_STEP_PROBABILITY * 100).toInt()}%, late=${(LATE_STEP_PROBABILITY * 100).toInt()}%)")
    }

    /**
     * Check if we should auto-resume simulation after a crash restart.
     * Clears the flag after reading.
     */
    fun shouldResumeSimulation(): Boolean {
        val wasSimulating = prefs?.getBoolean(KEY_WAS_SIMULATING, false) ?: false
        Log.d(TAG, "shouldResumeSimulation check: was_simulating=$wasSimulating")
        if (wasSimulating) {
            prefs?.edit()?.putBoolean(KEY_WAS_SIMULATING, false)?.commit()
            Log.d(TAG, "Cleared was_simulating flag, will resume simulation")
        }
        return wasSimulating
    }

    /**
     * Mark that simulation is active (called before force quit)
     */
    private fun setSimulationActive() {
        // Use commit() for synchronous write to ensure it's saved before process death
        prefs?.edit()?.putBoolean(KEY_WAS_SIMULATING, true)?.commit()
        Log.d(TAG, "Simulation state saved (was_simulating=true)")
    }

    /**
     * Get the probability for a given step number.
     * Steps 2-4 (early): low probability
     * Steps 5-7 (late): high probability
     */
    private fun getProbabilityForStep(step: Int): Float {
        return if (step >= 5) LATE_STEP_PROBABILITY else EARLY_STEP_PROBABILITY
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
     * Force quit the app. Restart is handled by external adb script (restart_monitor.sh).
     * @param reason Reason for the force quit (for logging)
     */
    fun forceQuitAndRestart(reason: String = "manual") {
        // Mark that simulation was active so we resume on restart
        setSimulationActive()

        ScreenLogger.logInfo(
            "force_quit_triggered",
            mapOf("reason" to reason)
        )
        Log.w(TAG, "Force quit triggered at step: $reason")

        // Small delay to ensure prefs and log are written before process death
        Thread.sleep(100)

        // Kill the process - restart is handled by external restart_monitor.sh script
        Process.killProcess(Process.myPid())
    }
}
