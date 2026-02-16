//
//  ContentView.swift
//  support-sankey-demo
//
//  A demo app to illustrate user journey flows for Sankey chart visualization
//
//  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
//  BITDRIFT USER JOURNEY SCREEN NAMES (in order)
//  Use these exact names when configuring User Journeys in Bitdrift dashboard
//  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
//
//  Step 1: Welcome
//  Step 2: Browse, Search
//  Step 3: Featured, Categories
//  Step 4: ProductDetail, Reviews
//  Step 5: Cart, Wishlist
//  Step 6: CheckoutGuest, CheckoutSignIn
//  Step 6: PaymentCard, PaymentApplePay, PaymentPayPal
//  Step 7: Confirmation
//
//  ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
//

import SwiftUI
import Combine
import Capture

// MARK: - Screen Logger
/// Centralized logging for screen views and user actions using Bitdrift SDK.
/// Uses Logger.logScreenView() for User Journey tracking in Sankey charts.
final class ScreenLogger {
    static let shared = ScreenLogger()

    private init() {}

    // MARK: - Screen View (for User Journey / Sankey charts)

    /// Logs a screen view event using Bitdrift's dedicated screen view API.
    /// This is the primary event for Sankey chart / User Journey generation.
    func logScreenView(_ screenName: String) {
        // Bitdrift SDK: Log screen view for user journey / Sankey chart tracking
        Logger.logScreenView(screenName: screenName)
        print("_screen_name: \(screenName)")
    }

    // MARK: - General logging methods

    /// Logs an info-level message with optional fields.
    func logInfo(_ message: String, fields: [String: String] = [:]) {
        // Bitdrift SDK: Log info-level message with custom fields
        Logger.logInfo(message, fields: fields)
        printLog(level: "INFO", message: message, fields: fields)
    }

    /// Logs a debug-level message with optional fields.
    func logDebug(_ message: String, fields: [String: String] = [:]) {
        // Bitdrift SDK: Log debug-level message with custom fields
        Logger.logDebug(message, fields: fields)
        printLog(level: "DEBUG", message: message, fields: fields)
    }

    /// Logs a warning-level message with optional fields.
    func logWarning(_ message: String, fields: [String: String] = [:]) {
        // Bitdrift SDK: Log warning-level message with custom fields
        Logger.logWarning(message, fields: fields)
        printLog(level: "WARNING", message: message, fields: fields)
    }

    // MARK: - Convenience methods for simulation events

    /// Logs simulation start event.
    func logSimulationStart(runs: Int) {
        logInfo("simulation_start", fields: ["total_runs": String(runs)])
    }

    /// Logs simulation end event.
    func logSimulationEnd(runs: Int) {
        logInfo("simulation_end", fields: ["total_runs": String(runs)])
    }

    // MARK: - Private helpers

    private func printLog(level: String, message: String, fields: [String: String]) {
        var output = "[\(level)] \(message)"
        if !fields.isEmpty {
            let fieldStrings = fields.map { "\($0.key)=\($0.value)" }.sorted()
            output += " | " + fieldStrings.joined(separator: " | ")
        }
        print(output)
    }
}

// MARK: - Simulation Manager
/// Handles automated simulation of user journeys through the app.
/// Randomly selects paths at each decision point to generate varied journey data.
@MainActor
class SimulationManager: ObservableObject {
    @Published var isSimulating = false
    @Published var currentRun = 0
    @Published var totalRuns = 0

    /// Flag to signal cancellation
    private var isCancelled = false

    /// Delay between navigation steps (in seconds)
    private let stepDelay: Double = 0.05

    /// Cancels the current simulation
    func cancel() {
        isCancelled = true
        ScreenLogger.shared.logInfo("simulation_cancelled", fields: [
            "completed_runs": String(currentRun),
            "total_runs": String(totalRuns)
        ])
    }

    /// Indicates infinite simulation mode (-1 means infinite)
    var isInfiniteMode: Bool { totalRuns == -1 }

    /// Runs the simulation for a specified number of journeys
    func simulate(runs: Int, path: Binding<NavigationPath>) async {
        isSimulating = true
        isCancelled = false
        totalRuns = runs
        currentRun = 0

        ScreenLogger.shared.logSimulationStart(runs: runs)

        for i in 1...runs {
            // Check for cancellation before each run
            if isCancelled { break }

            currentRun = i
            await runSingleJourney(path: path)

            // Check for cancellation after each run
            if isCancelled { break }

            // Small delay between runs
            try? await Task.sleep(nanoseconds: 50_000_000) // 0.05s
        }

        let completedRuns = isCancelled ? currentRun : runs
        ScreenLogger.shared.logSimulationEnd(runs: completedRuns)

        // Reset navigation to welcome screen
        path.wrappedValue = NavigationPath()

        isSimulating = false
        currentRun = 0
        totalRuns = 0
        isCancelled = false
    }

    /// Runs infinite simulation until cancelled
    func infiniteSimulate(path: Binding<NavigationPath>) async {
        isSimulating = true
        isCancelled = false
        totalRuns = -1  // -1 indicates infinite mode
        currentRun = 0

        ScreenLogger.shared.logInfo("infinite_simulation_start", fields: [:])

        while !isCancelled {
            currentRun += 1
            await runSingleJourney(path: path)

            // Small delay between runs
            try? await Task.sleep(nanoseconds: 50_000_000) // 0.05s
        }

        ScreenLogger.shared.logInfo("infinite_simulation_end", fields: [
            "total_runs": String(currentRun)
        ])

        // Reset navigation to welcome screen
        path.wrappedValue = NavigationPath()

        isSimulating = false
        currentRun = 0
        totalRuns = 0
        isCancelled = false
    }

    /// Executes a single random journey through the app
    private func runSingleJourney(path: Binding<NavigationPath>) async {
        // Generate new session ID for this journey
        Logger.startNewSession()
        
        // Reset to start
        path.wrappedValue = NavigationPath()
        try? await Task.sleep(nanoseconds: UInt64(stepDelay * 1_000_000_000))

        // Step 2: Browse or Search
        let step2: Screen = Bool.random() ? .browse : .search
        path.wrappedValue.append(step2)
        try? await Task.sleep(nanoseconds: UInt64(stepDelay * 1_000_000_000))

        // Step 3: Featured or Categories
        let step3: Screen = Bool.random() ? .featuredProducts : .categories
        path.wrappedValue.append(step3)
        try? await Task.sleep(nanoseconds: UInt64(stepDelay * 1_000_000_000))

        // Step 4: Product Detail or Reviews
        let source = step3 == .featuredProducts ? "featured" : "categories"
        let step4: Screen = Bool.random() ? .productDetail(source: source) : .reviews(source: source)
        path.wrappedValue.append(step4)
        try? await Task.sleep(nanoseconds: UInt64(stepDelay * 1_000_000_000))

        // Step 5: Cart or Wishlist
        let step5: Screen = Bool.random() ? .cart : .wishlist
        path.wrappedValue.append(step5)
        try? await Task.sleep(nanoseconds: UInt64(stepDelay * 1_000_000_000))

        // Step 6a: Guest or Sign In checkout
        let step6a: Screen = Bool.random() ? .checkoutGuest : .checkoutSignIn
        path.wrappedValue.append(step6a)
        try? await Task.sleep(nanoseconds: UInt64(stepDelay * 1_000_000_000))

        // Step 6b: Payment method (depends on checkout type)
        let step6b: Screen
        if step6a == .checkoutGuest {
            step6b = Bool.random() ? .paymentCard : .paymentApplePay
        } else {
            step6b = Bool.random() ? .paymentCard : .paymentPayPal
        }
        path.wrappedValue.append(step6b)
        try? await Task.sleep(nanoseconds: UInt64(stepDelay * 1_000_000_000))

        // Step 7: Confirmation (all paths converge)
        path.wrappedValue.append(Screen.confirmation)
        try? await Task.sleep(nanoseconds: UInt64(stepDelay * 1_000_000_000))

        // Reset for next run
        path.wrappedValue = NavigationPath()
    }
}

// MARK: - Navigation Path
enum Screen: Hashable {
    // Step 1
    case welcome
    // Step 2 branches
    case browse
    case search
    // Step 3 branches
    case featuredProducts
    case categories
    // Step 4 branches
    case productDetail(source: String)
    case reviews(source: String)
    // Step 5 branches
    case cart
    case wishlist
    // Step 6 branches
    case checkoutGuest
    case checkoutSignIn
    // Step 7 branches (all converge to confirmation)
    case paymentCard
    case paymentApplePay
    case paymentPayPal
    // Final step
    case confirmation
}

// MARK: - Main Content View
struct ContentView: View {
    @State private var path = NavigationPath()
    @StateObject private var simulationManager = SimulationManager()

    var body: some View {
        ZStack {
            NavigationStack(path: $path) {
                WelcomeView(path: $path, simulationManager: simulationManager)
                    .navigationDestination(for: Screen.self) { screen in
                        switch screen {
                        case .welcome:
                            WelcomeView(path: $path, simulationManager: simulationManager)
                        case .browse:
                            BrowseView(path: $path)
                        case .search:
                            SearchView(path: $path)
                        case .featuredProducts:
                            FeaturedProductsView(path: $path)
                        case .categories:
                            CategoriesView(path: $path)
                        case .productDetail(let source):
                            ProductDetailView(path: $path, source: source)
                        case .reviews(let source):
                            ReviewsView(path: $path, source: source)
                        case .cart:
                            CartView(path: $path)
                        case .wishlist:
                            WishlistView(path: $path)
                        case .checkoutGuest:
                            CheckoutGuestView(path: $path)
                        case .checkoutSignIn:
                            CheckoutSignInView(path: $path)
                        case .paymentCard:
                            PaymentCardView(path: $path)
                        case .paymentApplePay:
                            PaymentApplePayView(path: $path)
                        case .paymentPayPal:
                            PaymentPayPalView(path: $path)
                        case .confirmation:
                            ConfirmationView(path: $path)
                        }
                    }
            }

            // Floating simulation overlay - visible on all screens during simulation
            if simulationManager.isSimulating {
                VStack {
                    Spacer()
                    SimulationOverlay(simulationManager: simulationManager)
                        .padding()
                }
            }
        }
    }
}

/// Floating overlay shown during simulation with progress and cancel button
struct SimulationOverlay: View {
    @ObservedObject var simulationManager: SimulationManager

    var body: some View {
        VStack(spacing: 12) {
            HStack(spacing: 12) {
                ProgressView()
                    .tint(.white)
                Text(simulationManager.isInfiniteMode
                     ? "Simulating \(simulationManager.currentRun)/∞"
                     : "Simulating \(simulationManager.currentRun)/\(simulationManager.totalRuns)")
                    .font(.subheadline.bold())
                    .foregroundColor(.white)
                Spacer()
                Button(action: {
                    simulationManager.cancel()
                }) {
                    Image(systemName: "xmark.circle.fill")
                        .font(.title2)
                        .foregroundColor(.white.opacity(0.8))
                }
            }
            .padding()
            .background(Color.black.opacity(0.8))
            .cornerRadius(16)
        }
    }
}

// MARK: - Reusable Components
struct StepIndicator: View {
    let current: Int
    let total: Int = 7

    var body: some View {
        HStack(spacing: 8) {
            ForEach(1...total, id: \.self) { step in
                Circle()
                    .fill(step <= current ? Color.blue : Color.gray.opacity(0.3))
                    .frame(width: 12, height: 12)
            }
        }
        .padding(.vertical, 8)
    }
}

struct ScreenContainer<Content: View>: View {
    let screenName: String
    let title: String
    let subtitle: String
    let step: Int
    let icon: String
    let color: Color
    @ViewBuilder let content: Content

    var body: some View {
        VStack(spacing: 24) {
            StepIndicator(current: step)

            Spacer()

            VStack(spacing: 16) {
                ZStack {
                    Circle()
                        .fill(color.opacity(0.15))
                        .frame(width: 100, height: 100)
                    Image(systemName: icon)
                        .font(.system(size: 44))
                        .foregroundColor(color)
                }

                Text(title)
                    .font(.title)
                    .fontWeight(.bold)

                Text(subtitle)
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                    .multilineTextAlignment(.center)
                    .padding(.horizontal)
            }

            Spacer()

            VStack(spacing: 12) {
                content
            }
            .padding(.horizontal, 24)
            .padding(.bottom, 32)
        }
        .navigationTitle("Step \(step)")
        .navigationBarTitleDisplayMode(.inline)
        .onAppear {
            // bitdrift user journey logging
            ScreenLogger.shared.logScreenView(screenName)
        }
    }
}

struct PrimaryButton: View {
    let title: String
    let icon: String
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            HStack {
                Image(systemName: icon)
                Text(title)
                Spacer()
                Image(systemName: "chevron.right")
            }
            .font(.headline)
            .foregroundColor(.white)
            .padding()
            .background(Color.blue)
            .cornerRadius(12)
        }
    }
}

struct SecondaryButton: View {
    let title: String
    let icon: String
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            HStack {
                Image(systemName: icon)
                Text(title)
                Spacer()
                Image(systemName: "chevron.right")
            }
            .font(.headline)
            .foregroundColor(.blue)
            .padding()
            .background(Color.blue.opacity(0.1))
            .cornerRadius(12)
        }
    }
}

// MARK: - Step 1: Welcome
struct WelcomeView: View {
    @Binding var path: NavigationPath
    @ObservedObject var simulationManager: SimulationManager
    @State private var supportCode: String?
    @State private var showSupportCode = false
    @State private var supportCodeError: String?
    @State private var supportLogEnabled = false

    var body: some View {
        ScreenContainer(
            screenName: "Welcome",
            title: "Welcome to ShopDemo",
            subtitle: "Experience different shopping journeys",
            step: 1,
            icon: "cart.fill",
            color: .blue
        ) {
            // Manual navigation buttons
            PrimaryButton(title: "Browse Products", icon: "square.grid.2x2") {
                path.append(Screen.browse)
            }
            .disabled(simulationManager.isSimulating)

            SecondaryButton(title: "Search for Items", icon: "magnifyingglass") {
                path.append(Screen.search)
            }
            .disabled(simulationManager.isSimulating)

            // Simulation section
            Divider()
                .padding(.vertical, 8)

            if simulationManager.isSimulating {
                // Show message during simulation (cancel button is in floating overlay)
                Text("Simulation in progress...")
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                    .padding()
            } else {
                // Simulation buttons
                HStack(spacing: 8) {
                    SimButton(title: "Sim 10", color: .orange) {
                        Task {
                            await simulationManager.simulate(runs: 10, path: $path)
                        }
                    }
                    SimButton(title: "Sim 100", color: .red) {
                        Task {
                            await simulationManager.simulate(runs: 100, path: $path)
                        }
                    }
                    SimButton(title: "∞", color: .purple) {
                        Task {
                            await simulationManager.infiniteSimulate(path: $path)
                        }
                    }
                }

                // Support buttons
                HStack(spacing: 8) {
                    Button(action: {
                        Logger.createTemporaryDeviceCode { result in
                            DispatchQueue.main.async {
                                switch result {
                                case .success(let code):
                                    supportCode = code
                                    supportCodeError = nil
                                case .failure(let error):
                                    supportCode = nil
                                    supportCodeError = error.localizedDescription
                                }
                                showSupportCode = true
                            }
                        }
                    }) {
                        Text("Support Code")
                            .font(.caption.bold())
                            .foregroundColor(.green)
                            .padding(.horizontal, 16)
                            .padding(.vertical, 8)
                            .background(Color.green.opacity(0.15))
                            .cornerRadius(8)
                    }
                    .alert(supportCode != nil ? "Support Code" : "Error", isPresented: $showSupportCode) {
                        if let code = supportCode {
                            Button("Copy", action: {
                                UIPasteboard.general.string = code
                            })
                        }
                        Button("OK", role: .cancel) {}
                    } message: {
                        if let code = supportCode {
                            Text("\(code)\n\nShare this code with support to debug your session.")
                        } else if let error = supportCodeError {
                            Text("Failed to generate code: \(error)")
                        }
                    }

                    Button(action: {
                        supportLogEnabled.toggle()
                        Logger.addField(withKey: "supportlog", value: supportLogEnabled ? "true" : "false")
                    }) {
                        Text("Support Log")
                            .font(.caption.bold())
                            .foregroundColor(supportLogEnabled ? .white : .teal)
                            .padding(.horizontal, 16)
                            .padding(.vertical, 8)
                            .background(supportLogEnabled ? Color.teal : Color.teal.opacity(0.15))
                            .cornerRadius(8)
                    }
                }
                .padding(.top, 4)
            }
        }
    }
}

/// Button style for simulation controls
struct SimButton: View {
    let title: String
    let color: Color
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            Text(title)
                .font(.subheadline.bold())
                .foregroundColor(.white)
                .frame(maxWidth: .infinity)
                .padding(.vertical, 10)
                .background(color)
                .cornerRadius(10)
        }
    }
}

// MARK: - Step 2: Browse / Search
struct BrowseView: View {
    @Binding var path: NavigationPath

    var body: some View {
        ScreenContainer(
            screenName: "Browse",
            title: "Browse",
            subtitle: "Explore our product catalog",
            step: 2,
            icon: "square.grid.2x2.fill",
            color: .purple
        ) {
            PrimaryButton(title: "View Featured", icon: "star.fill") {
                path.append(Screen.featuredProducts)
            }
            SecondaryButton(title: "Shop by Category", icon: "folder.fill") {
                path.append(Screen.categories)
            }
        }
    }
}

struct SearchView: View {
    @Binding var path: NavigationPath

    var body: some View {
        ScreenContainer(
            screenName: "Search",
            title: "Search",
            subtitle: "Find exactly what you're looking for",
            step: 2,
            icon: "magnifyingglass",
            color: .orange
        ) {
            PrimaryButton(title: "View Featured", icon: "star.fill") {
                path.append(Screen.featuredProducts)
            }
            SecondaryButton(title: "Shop by Category", icon: "folder.fill") {
                path.append(Screen.categories)
            }
        }
    }
}

// MARK: - Step 3: Featured Products / Categories
struct FeaturedProductsView: View {
    @Binding var path: NavigationPath

    var body: some View {
        ScreenContainer(
            screenName: "Featured",
            title: "Featured Products",
            subtitle: "Our top picks for you",
            step: 3,
            icon: "star.fill",
            color: .yellow
        ) {
            PrimaryButton(title: "View Product Details", icon: "eye.fill") {
                path.append(Screen.productDetail(source: "featured"))
            }
            SecondaryButton(title: "Read Reviews First", icon: "text.bubble.fill") {
                path.append(Screen.reviews(source: "featured"))
            }
        }
    }
}

struct CategoriesView: View {
    @Binding var path: NavigationPath

    var body: some View {
        ScreenContainer(
            screenName: "Categories",
            title: "Categories",
            subtitle: "Browse by product type",
            step: 3,
            icon: "folder.fill",
            color: .green
        ) {
            PrimaryButton(title: "View Product Details", icon: "eye.fill") {
                path.append(Screen.productDetail(source: "categories"))
            }
            SecondaryButton(title: "Read Reviews First", icon: "text.bubble.fill") {
                path.append(Screen.reviews(source: "categories"))
            }
        }
    }
}

// MARK: - Step 4: Product Detail / Reviews
struct ProductDetailView: View {
    @Binding var path: NavigationPath
    let source: String

    var body: some View {
        ScreenContainer(
            screenName: "ProductDetail",
            title: "Product Details",
            subtitle: "Premium Wireless Headphones",
            step: 4,
            icon: "headphones",
            color: .cyan
        ) {
            PrimaryButton(title: "Add to Cart", icon: "cart.badge.plus") {
                path.append(Screen.cart)
            }
            SecondaryButton(title: "Save to Wishlist", icon: "heart.fill") {
                path.append(Screen.wishlist)
            }
        }
    }
}

struct ReviewsView: View {
    @Binding var path: NavigationPath
    let source: String

    var body: some View {
        ScreenContainer(
            screenName: "Reviews",
            title: "Customer Reviews",
            subtitle: "4.8 stars from 2,431 reviews",
            step: 4,
            icon: "text.bubble.fill",
            color: .mint
        ) {
            PrimaryButton(title: "Add to Cart", icon: "cart.badge.plus") {
                path.append(Screen.cart)
            }
            SecondaryButton(title: "Save to Wishlist", icon: "heart.fill") {
                path.append(Screen.wishlist)
            }
        }
    }
}

// MARK: - Step 5: Cart / Wishlist
struct CartView: View {
    @Binding var path: NavigationPath

    var body: some View {
        ScreenContainer(
            screenName: "Cart",
            title: "Shopping Cart",
            subtitle: "1 item - $299.00",
            step: 5,
            icon: "cart.fill",
            color: .blue
        ) {
            PrimaryButton(title: "Checkout as Guest", icon: "person.fill") {
                path.append(Screen.checkoutGuest)
            }
            SecondaryButton(title: "Sign In to Checkout", icon: "person.badge.key.fill") {
                path.append(Screen.checkoutSignIn)
            }
        }
    }
}

struct WishlistView: View {
    @Binding var path: NavigationPath

    var body: some View {
        ScreenContainer(
            screenName: "Wishlist",
            title: "Wishlist",
            subtitle: "Items you've saved for later",
            step: 5,
            icon: "heart.fill",
            color: .pink
        ) {
            PrimaryButton(title: "Checkout as Guest", icon: "person.fill") {
                path.append(Screen.checkoutGuest)
            }
            SecondaryButton(title: "Sign In to Checkout", icon: "person.badge.key.fill") {
                path.append(Screen.checkoutSignIn)
            }
        }
    }
}

// MARK: - Step 6: Checkout Options
struct CheckoutGuestView: View {
    @Binding var path: NavigationPath

    var body: some View {
        ScreenContainer(
            screenName: "CheckoutGuest",
            title: "Guest Checkout",
            subtitle: "Complete your purchase",
            step: 6,
            icon: "person.fill",
            color: .indigo
        ) {
            PrimaryButton(title: "Pay with Card", icon: "creditcard.fill") {
                path.append(Screen.paymentCard)
            }
            SecondaryButton(title: "Apple Pay", icon: "apple.logo") {
                path.append(Screen.paymentApplePay)
            }
        }
    }
}

struct CheckoutSignInView: View {
    @Binding var path: NavigationPath

    var body: some View {
        ScreenContainer(
            screenName: "CheckoutSignIn",
            title: "Member Checkout",
            subtitle: "Signed in as user@example.com",
            step: 6,
            icon: "person.badge.key.fill",
            color: .teal
        ) {
            PrimaryButton(title: "Pay with Card", icon: "creditcard.fill") {
                path.append(Screen.paymentCard)
            }
            SecondaryButton(title: "PayPal", icon: "p.circle.fill") {
                path.append(Screen.paymentPayPal)
            }
        }
    }
}

// MARK: - Step 6b: Payment Methods (all lead to confirmation)
struct PaymentCardView: View {
    @Binding var path: NavigationPath

    var body: some View {
        ScreenContainer(
            screenName: "PaymentCard",
            title: "Card Payment",
            subtitle: "Enter your card details",
            step: 6,
            icon: "creditcard.fill",
            color: .blue
        ) {
            PrimaryButton(title: "Complete Purchase", icon: "checkmark.circle.fill") {
                path.append(Screen.confirmation)
            }
        }
    }
}

struct PaymentApplePayView: View {
    @Binding var path: NavigationPath

    var body: some View {
        ScreenContainer(
            screenName: "PaymentApplePay",
            title: "Apple Pay",
            subtitle: "Confirm with Face ID",
            step: 6,
            icon: "apple.logo",
            color: .black
        ) {
            PrimaryButton(title: "Complete Purchase", icon: "checkmark.circle.fill") {
                path.append(Screen.confirmation)
            }
        }
    }
}

struct PaymentPayPalView: View {
    @Binding var path: NavigationPath

    var body: some View {
        ScreenContainer(
            screenName: "PaymentPayPal",
            title: "PayPal",
            subtitle: "Redirecting to PayPal...",
            step: 6,
            icon: "p.circle.fill",
            color: .blue
        ) {
            PrimaryButton(title: "Complete Purchase", icon: "checkmark.circle.fill") {
                path.append(Screen.confirmation)
            }
        }
    }
}

// MARK: - Step 7: Confirmation (Final - All Paths Converge)
struct ConfirmationView: View {
    @Binding var path: NavigationPath

    var body: some View {
        VStack(spacing: 24) {
            StepIndicator(current: 7)

            Spacer()

            VStack(spacing: 20) {
                ZStack {
                    Circle()
                        .fill(Color.green.opacity(0.15))
                        .frame(width: 120, height: 120)
                    Image(systemName: "checkmark.circle.fill")
                        .font(.system(size: 60))
                        .foregroundColor(.green)
                }

                Text("Order Confirmed!")
                    .font(.title)
                    .fontWeight(.bold)

                Text("Thank you for your purchase.\nOrder #\(Int.random(in: 10000...99999))")
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                    .multilineTextAlignment(.center)

                VStack(spacing: 8) {
                    Text("🎉")
                        .font(.system(size: 40))
                    Text("Journey Complete!")
                        .font(.headline)
                        .foregroundColor(.blue)
                }
                .padding(.top, 20)
            }

            Spacer()

            Button(action: {
                path = NavigationPath()
            }) {
                HStack {
                    Image(systemName: "arrow.counterclockwise")
                    Text("Start New Journey")
                }
                .font(.headline)
                .foregroundColor(.white)
                .frame(maxWidth: .infinity)
                .padding()
                .background(Color.green)
                .cornerRadius(12)
            }
            .padding(.horizontal, 24)
            .padding(.bottom, 32)
        }
        .navigationTitle("Step 7")
        .navigationBarTitleDisplayMode(.inline)
        .navigationBarBackButtonHidden(true)
        .onAppear {
            // bitdrift user journey logging
            ScreenLogger.shared.logScreenView("Confirmation")
        }
    }
}

#Preview {
    ContentView()
}
