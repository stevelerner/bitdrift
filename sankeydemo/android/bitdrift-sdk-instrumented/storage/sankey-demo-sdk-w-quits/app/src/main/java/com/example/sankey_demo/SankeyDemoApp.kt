package com.example.sankey_demo

// bitdrift
import io.bitdrift.capture.Capture.Logger
import io.bitdrift.capture.providers.session.SessionStrategy

import android.app.Application
import android.util.Log

class SankeyDemoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize bitdrift
        Logger.start(
            apiKey = "YOURKEYHERE",
            sessionStrategy = SessionStrategy.Fixed(),
        )

        // Register lifecycle callbacks
        registerActivityLifecycleCallbacks(AppLifecycleCallbacks())

        // Initialize the restart manager for random force quit functionality
        AppRestartManager.initialize(this)

        // Log app launch
        Log.d(TAG, "App launched")
    }

    companion object {
        private const val TAG = "SankeyDemoApp"
    }
}
