package com.example.sankey_demo

import android.app.Application
import android.util.Log

class SankeyDemoApp : Application() {

    override fun onCreate() {
        super.onCreate()

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
