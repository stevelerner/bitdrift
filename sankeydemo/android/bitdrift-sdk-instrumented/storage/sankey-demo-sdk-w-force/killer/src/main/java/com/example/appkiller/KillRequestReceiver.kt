package com.example.appkiller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Receives kill requests from the demo app.
 * Forwards to the accessibility service if running.
 */
class KillRequestReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "KillRequestReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == AppKillerService.ACTION_KILL_APP) {
            val packageName = intent.getStringExtra(AppKillerService.EXTRA_PACKAGE_NAME)
            Log.d(TAG, "Received kill request for: $packageName")

            val service = AppKillerService.instance
            if (service != null && packageName != null) {
                service.killApp(packageName)
            } else {
                Log.w(TAG, "Service not running or package name missing")
            }
        }
    }
}
