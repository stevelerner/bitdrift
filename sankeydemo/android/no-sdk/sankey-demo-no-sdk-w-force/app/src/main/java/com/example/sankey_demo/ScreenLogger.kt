package com.example.sankey_demo

import android.util.Log

/**
 * Centralized logging for screen views and user actions.
 * Logs to Android logcat only (no SDK).
 */
object ScreenLogger {

    private const val TAG = "ScreenLogger"

    fun logScreenView(screenName: String) {
        Log.d(TAG, "_screen_name: $screenName")
    }

    fun logInfo(message: String, fields: Map<String, String> = emptyMap()) {
        printLog("INFO", message, fields)
    }

    fun logSimulationStart(runs: Int) {
        logInfo("simulation_start", mapOf("total_runs" to runs.toString()))
    }

    fun logSimulationEnd(runs: Int) {
        logInfo("simulation_end", mapOf("total_runs" to runs.toString()))
    }

    private fun printLog(level: String, message: String, fields: Map<String, String>) {
        val output = buildString {
            append("[$level] $message")
            if (fields.isNotEmpty()) {
                append(" | ")
                append(fields.entries.sortedBy { it.key }.joinToString(" | ") { "${it.key}=${it.value}" })
            }
        }
        Log.d(TAG, output)
    }
}
