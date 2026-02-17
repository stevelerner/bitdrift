package com.example.appkiller

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var statusText: TextView
    private lateinit var enableButton: Button
    private lateinit var testButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.status_text)
        enableButton = findViewById(R.id.enable_button)
        testButton = findViewById(R.id.test_button)

        enableButton.setOnClickListener {
            // Open accessibility settings
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }

        testButton.setOnClickListener {
            // Test kill on demo app
            if (AppKillerService.isRunning()) {
                AppKillerService.instance?.killApp("com.example.sankey_demo")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateStatus()
    }

    private fun updateStatus() {
        val isRunning = AppKillerService.isRunning()
        statusText.text = if (isRunning) {
            "Service Status: ENABLED\n\nThe killer service is running and ready to receive kill requests from the demo app."
        } else {
            "Service Status: DISABLED\n\nPlease enable the App Killer accessibility service to allow swipe-to-kill functionality."
        }
        enableButton.text = if (isRunning) "Open Accessibility Settings" else "Enable Service"
        testButton.isEnabled = isRunning
    }
}
