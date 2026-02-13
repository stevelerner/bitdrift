//
//  sankey_demoApp.swift
//  sankey-demo
//
//  Created by sl on 1/29/26.
//

import SwiftUI
import Capture

@main
struct sankey_demoApp: App {

    init() {
        // Initialize Bitdrift SDK
        let apiKey = "YOURKEYHERE"

        // Bitdrift SDK: Initialize the Capture logger with API key and session strategy
        Logger.start(
            withAPIKey: apiKey,
            sessionStrategy: .fixed()
        )?
        .enableIntegrations([.urlSession()])

        // Bitdrift SDK: Create a temporary device code for dashboard linking
        Logger.createTemporaryDeviceCode { result in
            switch result {
            case .success(let code):
                print("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
                print("🔑 BITDRIFT DEVICE CODE: \(code)")
                print("📱 Use this code in the bitdrift dashboard")
                print("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
            case .failure(let error):
                print("⚠️ Failed to create device code: \(error)")
            }
        }

        // Register for app lifecycle notifications
        setupLifecycleObservers()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }

    // MARK: - Lifecycle Observability

    private func setupLifecycleObservers() {
        let notificationCenter = NotificationCenter.default

        // App Open - foreground
        notificationCenter.addObserver(
            forName: UIApplication.willEnterForegroundNotification,
            object: nil,
            queue: .main
        ) { _ in
            // Bitdrift SDK: Log info-level message with custom fields
            Logger.logInfo("app_open", fields: ["trigger": "willEnterForeground"])
        }

        // App Close - background
        notificationCenter.addObserver(
            forName: UIApplication.didEnterBackgroundNotification,
            object: nil,
            queue: .main
        ) { _ in
            // Bitdrift SDK: Log info-level message with custom fields
            Logger.logInfo("app_close", fields: ["trigger": "didEnterBackground"])
        }

        // App Will Terminate
        notificationCenter.addObserver(
            forName: UIApplication.willTerminateNotification,
            object: nil,
            queue: .main
        ) { _ in
            // Bitdrift SDK: Log info-level message
            Logger.logInfo("app_will_terminate", fields: [:])
        }

        // Memory Pressure
        notificationCenter.addObserver(
            forName: UIApplication.didReceiveMemoryWarningNotification,
            object: nil,
            queue: .main
        ) { _ in
            // Bitdrift SDK: Log warning-level message
            Logger.logWarning("memory_pressure", fields: [:])
        }

        // Low Power Mode
        notificationCenter.addObserver(
            forName: NSNotification.Name.NSProcessInfoPowerStateDidChange,
            object: nil,
            queue: .main
        ) { _ in
            let isLowPower = ProcessInfo.processInfo.isLowPowerModeEnabled
            // Bitdrift SDK: Log info-level message with custom fields
            Logger.logInfo("low_power_mode", fields: ["enabled": String(isLowPower)])
        }

        // Bitdrift SDK: Log info-level message for app launch
        Logger.logInfo("app_did_finish_launching", fields: [:])
    }
}
