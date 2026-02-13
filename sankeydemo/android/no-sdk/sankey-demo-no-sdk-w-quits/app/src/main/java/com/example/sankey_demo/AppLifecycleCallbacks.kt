package com.example.sankey_demo

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.os.Bundle

/**
 * Tracks app lifecycle events and logs them to bitdrift.
 */
class AppLifecycleCallbacks : Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    private var activityCount = 0

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
    override fun onConfigurationChanged(newConfig: Configuration) {}

    override fun onActivityStarted(activity: Activity) {
        activityCount++
        if (activityCount == 1) {
            ScreenLogger.logInfo("app_open", mapOf("trigger" to "onActivityStarted"))
        }
    }

    override fun onActivityStopped(activity: Activity) {
        activityCount--
        if (activityCount == 0) {
            ScreenLogger.logInfo("app_close", mapOf("trigger" to "onActivityStopped"))
        }
    }

    override fun onTrimMemory(level: Int) {
        if (level >= ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW) {
            ScreenLogger.logInfo("memory_pressure", mapOf("level" to level.toString()))
        }
    }

    override fun onLowMemory() {
        ScreenLogger.logInfo("low_memory")
    }
}
