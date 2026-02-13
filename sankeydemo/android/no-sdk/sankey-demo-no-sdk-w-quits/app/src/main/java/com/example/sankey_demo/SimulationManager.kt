package com.example.sankey_demo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Handles automated simulation of user journeys through the app.
 * Randomly selects paths at each decision point to generate varied journey data.
 */
class SimulationManager : ViewModel() {

    var isSimulating by mutableStateOf(false)
        private set

    var currentRun by mutableIntStateOf(0)
        private set

    var totalRuns by mutableIntStateOf(0)
        private set

    // Flag to signal cancellation
    private var isCancelled = false

    // Delay between navigation steps (in milliseconds)
    private val stepDelay = 50L

    /**
     * Cancels the current simulation
     */
    fun cancel() {
        isCancelled = true
        ScreenLogger.logInfo(
            "simulation_cancelled",
            mapOf(
                "completed_runs" to currentRun.toString(),
                "total_runs" to totalRuns.toString()
            )
        )
    }

    /**
     * Indicates infinite simulation mode (-1 means infinite)
     */
    val isInfiniteMode: Boolean
        get() = totalRuns == -1

    /**
     * Runs the simulation for a specified number of journeys
     */
    fun simulate(runs: Int, navController: NavController) {
        viewModelScope.launch {
            isSimulating = true
            isCancelled = false
            totalRuns = runs
            currentRun = 0

            ScreenLogger.logSimulationStart(runs)

            for (i in 1..runs) {
                // Check for cancellation before each run
                if (isCancelled) break

                currentRun = i
                runSingleJourney(navController)

                // Check for cancellation after each run
                if (isCancelled) break

                // Small delay between runs
                delay(50L)
            }

            val completedRuns = if (isCancelled) currentRun else runs
            ScreenLogger.logSimulationEnd(completedRuns)

            // Reset navigation to welcome screen
            navController.navigate(Screen.Welcome.route) {
                popUpTo(Screen.Welcome.route) { inclusive = true }
            }

            isSimulating = false
            currentRun = 0
            totalRuns = 0
            isCancelled = false
        }
    }

    /**
     * Runs infinite simulation until cancelled
     */
    fun infiniteSimulate(navController: NavController) {
        viewModelScope.launch {
            isSimulating = true
            isCancelled = false
            totalRuns = -1  // -1 indicates infinite mode
            currentRun = 0

            ScreenLogger.logInfo("infinite_simulation_start", emptyMap())

            while (!isCancelled) {
                currentRun++
                runSingleJourney(navController)

                // Small delay between runs
                delay(50L)
            }

            ScreenLogger.logInfo("infinite_simulation_end", mapOf("total_runs" to currentRun.toString()))

            // Reset navigation to welcome screen
            navController.navigate(Screen.Welcome.route) {
                popUpTo(Screen.Welcome.route) { inclusive = true }
            }

            isSimulating = false
            currentRun = 0
            totalRuns = 0
            isCancelled = false
        }
    }

    /**
     * Executes a single random journey through the app.
     * May randomly force quit at any step based on AppRestartManager probability.
     */
    private suspend fun runSingleJourney(navController: NavController) {
        // Reset to start
        navController.navigate(Screen.Welcome.route) {
            popUpTo(Screen.Welcome.route) { inclusive = true }
        }
        delay(stepDelay)

        // Step 2: Browse or Search (low quit probability - 3%)
        val step2 = if ((0..1).random() == 0) Screen.Browse else Screen.Search
        navController.navigate(step2.route)
        delay(stepDelay)
        if (AppRestartManager.maybeForceQuit(step2.route, step = 2)) return

        // Step 3: Featured or Categories (low quit probability - 3%)
        val step3 = if ((0..1).random() == 0) Screen.FeaturedProducts else Screen.Categories
        navController.navigate(step3.route)
        delay(stepDelay)
        if (AppRestartManager.maybeForceQuit(step3.route, step = 3)) return

        // Step 4: Product Detail or Reviews (low quit probability - 3%)
        val source = if (step3 == Screen.FeaturedProducts) "featured" else "categories"
        val step4 = if ((0..1).random() == 0) Screen.ProductDetail(source) else Screen.Reviews(source)
        navController.navigate(step4.route)
        delay(stepDelay)
        if (AppRestartManager.maybeForceQuit(step4.route, step = 4)) return

        // Step 5: Cart or Wishlist (higher quit probability - 10%)
        val step5 = if ((0..1).random() == 0) Screen.Cart else Screen.Wishlist
        navController.navigate(step5.route)
        delay(stepDelay)
        if (AppRestartManager.maybeForceQuit(step5.route, step = 5)) return

        // Step 6a: Guest or Sign In checkout (higher quit probability - 10%)
        val step6a = if ((0..1).random() == 0) Screen.CheckoutGuest else Screen.CheckoutSignIn
        navController.navigate(step6a.route)
        delay(stepDelay)
        if (AppRestartManager.maybeForceQuit(step6a.route, step = 6)) return

        // Step 6b: Payment method (higher quit probability - 10%)
        val step6b = if (step6a == Screen.CheckoutGuest) {
            if ((0..1).random() == 0) Screen.PaymentCard else Screen.PaymentApplePay
        } else {
            if ((0..1).random() == 0) Screen.PaymentCard else Screen.PaymentPayPal
        }
        navController.navigate(step6b.route)
        delay(stepDelay)
        if (AppRestartManager.maybeForceQuit(step6b.route, step = 7)) return

        // Step 7: Confirmation (all paths converge)
        navController.navigate(Screen.Confirmation.route)
        delay(stepDelay)
        // Note: No force quit check on final step to allow journey completion logging
    }
}
