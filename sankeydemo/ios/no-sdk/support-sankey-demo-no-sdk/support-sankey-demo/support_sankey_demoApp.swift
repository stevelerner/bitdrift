//
//  support_sankey_demoApp.swift
//  support-sankey-demo
//
//  Created by sl on 1/29/26.
//

import SwiftUI

@main
struct support_sankey_demoApp: App {

    init() {
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
            print("[INFO] app_open | trigger=willEnterForeground")
        }

        // App Close - background
        notificationCenter.addObserver(
            forName: UIApplication.didEnterBackgroundNotification,
            object: nil,
            queue: .main
        ) { _ in
            print("[INFO] app_close | trigger=didEnterBackground")
        }

        // App Will Terminate
        notificationCenter.addObserver(
            forName: UIApplication.willTerminateNotification,
            object: nil,
            queue: .main
        ) { _ in
            print("[INFO] app_will_terminate")
        }

        // Memory Pressure
        notificationCenter.addObserver(
            forName: UIApplication.didReceiveMemoryWarningNotification,
            object: nil,
            queue: .main
        ) { _ in
            print("[WARNING] memory_pressure")
        }

        // Low Power Mode
        notificationCenter.addObserver(
            forName: NSNotification.Name.NSProcessInfoPowerStateDidChange,
            object: nil,
            queue: .main
        ) { _ in
            let isLowPower = ProcessInfo.processInfo.isLowPowerModeEnabled
            print("[INFO] low_power_mode | enabled=\(isLowPower)")
        }

        print("[INFO] app_did_finish_launching")
    }
}
