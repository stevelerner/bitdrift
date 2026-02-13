package com.example.sankey_demo

import android.app.Application
import android.util.Log

class SankeyDemoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Register lifecycle callbacks
        registerActivityLifecycleCallbacks(AppLifecycleCallbacks())

        // Log app launch
        Log.d(TAG, "App launched")
    }

    companion object {
        private const val TAG = "SankeyDemoApp"
    }
}
