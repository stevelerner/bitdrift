package com.example.sankey_demo

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log

/**
 * Service that runs in a separate process to restart the app after force quit.
 * The separate process (:restart) survives when the main app process is killed.
 */
class RestartService : Service() {

    companion object {
        private const val TAG = "RestartService"
        const val EXTRA_DELAY_MS = "delay_ms"
        const val DEFAULT_DELAY_MS = 1000L
    }

    private val handler = Handler(Looper.getMainLooper())

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val delayMs = intent?.getLongExtra(EXTRA_DELAY_MS, DEFAULT_DELAY_MS) ?: DEFAULT_DELAY_MS

        Log.d(TAG, "RestartService started, will restart app in ${delayMs}ms")

        handler.postDelayed({
            restartApp()
            stopSelf()
        }, delayMs)

        return START_NOT_STICKY
    }

    private fun restartApp() {
        Log.d(TAG, "Restarting app now")

        val intent = packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
            startActivity(intent)
        } else {
            Log.e(TAG, "Could not get launch intent")
        }
    }
}
