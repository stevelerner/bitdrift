package com.example.sankey_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sankey_demo.ui.theme.SankeydemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SankeydemoTheme {
                SankeyDemoContent()
            }
        }
    }
}

@Composable
fun SankeyDemoContent() {
    val navController = rememberNavController()
    val simulationManager: SimulationManager = viewModel()

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route
        ) {
            composable(Screen.Welcome.route) {
                WelcomeScreen(navController, simulationManager)
            }

            // Step 2
            composable(Screen.Browse.route) {
                BrowseScreen(navController)
            }
            composable(Screen.Search.route) {
                SearchScreen(navController)
            }

            // Step 3
            composable(Screen.FeaturedProducts.route) {
                FeaturedProductsScreen(navController)
            }
            composable(Screen.Categories.route) {
                CategoriesScreen(navController)
            }

            // Step 4 (with parameters)
            composable(
                route = Screen.PRODUCT_DETAIL_ROUTE,
                arguments = listOf(navArgument("source") { type = NavType.StringType })
            ) { backStackEntry ->
                val source = backStackEntry.arguments?.getString("source")
                ProductDetailScreen(navController, source)
            }
            composable(
                route = Screen.REVIEWS_ROUTE,
                arguments = listOf(navArgument("source") { type = NavType.StringType })
            ) { backStackEntry ->
                val source = backStackEntry.arguments?.getString("source")
                ReviewsScreen(navController, source)
            }

            // Step 5
            composable(Screen.Cart.route) {
                CartScreen(navController)
            }
            composable(Screen.Wishlist.route) {
                WishlistScreen(navController)
            }

            // Step 6
            composable(Screen.CheckoutGuest.route) {
                CheckoutGuestScreen(navController)
            }
            composable(Screen.CheckoutSignIn.route) {
                CheckoutSignInScreen(navController)
            }

            // Step 7 - Payment methods
            composable(Screen.PaymentCard.route) {
                PaymentCardScreen(navController)
            }
            composable(Screen.PaymentApplePay.route) {
                PaymentApplePayScreen(navController)
            }
            composable(Screen.PaymentPayPal.route) {
                PaymentPayPalScreen(navController)
            }

            // Final step
            composable(Screen.Confirmation.route) {
                ConfirmationScreen(navController)
            }
        }

        // Floating simulation overlay - visible on all screens during simulation
        if (simulationManager.isSimulating) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                SimulationOverlay(simulationManager)
            }
        }
    }
}
