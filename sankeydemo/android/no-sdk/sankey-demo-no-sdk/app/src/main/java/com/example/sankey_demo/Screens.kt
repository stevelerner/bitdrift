package com.example.sankey_demo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.random.Random

// MARK: - Step 1: Welcome

@Composable
fun WelcomeScreen(navController: NavController, simulationManager: SimulationManager) {
    ScreenContainer(
        screenName = "Welcome",
        title = "Welcome to ShopDemo",
        subtitle = "Experience different shopping journeys",
        step = 1,
        icon = Icons.Default.ShoppingCart,
        color = Color(0xFF2196F3) // Blue
    ) {
        // Manual navigation buttons
        PrimaryButton(
            title = "Browse Products",
            icon = Icons.Default.Menu,
            enabled = !simulationManager.isSimulating
        ) {
            navController.navigate(Screen.Browse.route)
        }

        SecondaryButton(
            title = "Search for Items",
            icon = Icons.Default.Search,
            enabled = !simulationManager.isSimulating
        ) {
            navController.navigate(Screen.Search.route)
        }

        // Simulation section
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        if (simulationManager.isSimulating) {
            // Show message during simulation (cancel button is in floating overlay)
            Text(
                text = "Simulation in progress...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(16.dp)
            )
        } else {
            // Simulation buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    SimButton(title = "Sim 10", color = Color(0xFFFF9800)) { // Orange
                        simulationManager.simulate(10, navController)
                    }
                }
                Box(modifier = Modifier.weight(1f)) {
                    SimButton(title = "Sim 100", color = Color(0xFFF44336)) { // Red
                        simulationManager.simulate(100, navController)
                    }
                }
            }

            // Infinite simulation button
            Button(
                onClick = {
                    simulationManager.infiniteSimulate(navController)
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9C27B0).copy(alpha = 0.15f),
                    contentColor = Color(0xFF9C27B0)
                ),
                modifier = Modifier
                    .padding(top = 4.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "∞ Infinite Sim",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

// MARK: - Step 2: Browse / Search

@Composable
fun BrowseScreen(navController: NavController) {
    ScreenContainer(
        screenName = "Browse",
        title = "Browse",
        subtitle = "Explore our product catalog",
        step = 2,
        icon = Icons.Default.Menu,
        color = Color(0xFF9C27B0) // Purple
    ) {
        PrimaryButton(
            title = "View Featured",
            icon = Icons.Default.Star
        ) {
            navController.navigate(Screen.FeaturedProducts.route)
        }
        SecondaryButton(
            title = "Shop by Category",
            icon = Icons.Default.List
        ) {
            navController.navigate(Screen.Categories.route)
        }
    }
}

@Composable
fun SearchScreen(navController: NavController) {
    ScreenContainer(
        screenName = "Search",
        title = "Search",
        subtitle = "Find exactly what you're looking for",
        step = 2,
        icon = Icons.Default.Search,
        color = Color(0xFFFF9800) // Orange
    ) {
        PrimaryButton(
            title = "View Featured",
            icon = Icons.Default.Star
        ) {
            navController.navigate(Screen.FeaturedProducts.route)
        }
        SecondaryButton(
            title = "Shop by Category",
            icon = Icons.Default.List
        ) {
            navController.navigate(Screen.Categories.route)
        }
    }
}

// MARK: - Step 3: Featured Products / Categories

@Composable
fun FeaturedProductsScreen(navController: NavController) {
    ScreenContainer(
        screenName = "Featured",
        title = "Featured Products",
        subtitle = "Our top picks for you",
        step = 3,
        icon = Icons.Default.Star,
        color = Color(0xFFFFEB3B) // Yellow
    ) {
        PrimaryButton(
            title = "View Product Details",
            icon = Icons.Default.Info
        ) {
            navController.navigate("productDetail/featured")
        }
        SecondaryButton(
            title = "Read Reviews First",
            icon = Icons.Default.Email
        ) {
            navController.navigate("reviews/featured")
        }
    }
}

@Composable
fun CategoriesScreen(navController: NavController) {
    ScreenContainer(
        screenName = "Categories",
        title = "Categories",
        subtitle = "Browse by product type",
        step = 3,
        icon = Icons.Default.List,
        color = Color(0xFF4CAF50) // Green
    ) {
        PrimaryButton(
            title = "View Product Details",
            icon = Icons.Default.Info
        ) {
            navController.navigate("productDetail/categories")
        }
        SecondaryButton(
            title = "Read Reviews First",
            icon = Icons.Default.Email
        ) {
            navController.navigate("reviews/categories")
        }
    }
}

// MARK: - Step 4: Product Detail / Reviews

@Composable
fun ProductDetailScreen(navController: NavController, source: String?) {
    ScreenContainer(
        screenName = "ProductDetail",
        title = "Product Details",
        subtitle = "Premium Wireless Headphones",
        step = 4,
        icon = Icons.Default.Notifications,
        color = Color(0xFF00BCD4) // Cyan
    ) {
        PrimaryButton(
            title = "Add to Cart",
            icon = Icons.Default.Add
        ) {
            navController.navigate(Screen.Cart.route)
        }
        SecondaryButton(
            title = "Save to Wishlist",
            icon = Icons.Default.Favorite
        ) {
            navController.navigate(Screen.Wishlist.route)
        }
    }
}

@Composable
fun ReviewsScreen(navController: NavController, source: String?) {
    ScreenContainer(
        screenName = "Reviews",
        title = "Customer Reviews",
        subtitle = "4.8 stars from 2,431 reviews",
        step = 4,
        icon = Icons.Default.Email,
        color = Color(0xFF00E5BD) // Mint
    ) {
        PrimaryButton(
            title = "Add to Cart",
            icon = Icons.Default.Add
        ) {
            navController.navigate(Screen.Cart.route)
        }
        SecondaryButton(
            title = "Save to Wishlist",
            icon = Icons.Default.Favorite
        ) {
            navController.navigate(Screen.Wishlist.route)
        }
    }
}

// MARK: - Step 5: Cart / Wishlist

@Composable
fun CartScreen(navController: NavController) {
    ScreenContainer(
        screenName = "Cart",
        title = "Shopping Cart",
        subtitle = "1 item - \$299.00",
        step = 5,
        icon = Icons.Default.ShoppingCart,
        color = Color(0xFF2196F3) // Blue
    ) {
        PrimaryButton(
            title = "Checkout as Guest",
            icon = Icons.Default.Person
        ) {
            navController.navigate(Screen.CheckoutGuest.route)
        }
        SecondaryButton(
            title = "Sign In to Checkout",
            icon = Icons.Default.Lock
        ) {
            navController.navigate(Screen.CheckoutSignIn.route)
        }
    }
}

@Composable
fun WishlistScreen(navController: NavController) {
    ScreenContainer(
        screenName = "Wishlist",
        title = "Wishlist",
        subtitle = "Items you've saved for later",
        step = 5,
        icon = Icons.Default.Favorite,
        color = Color(0xFFE91E63) // Pink
    ) {
        PrimaryButton(
            title = "Checkout as Guest",
            icon = Icons.Default.Person
        ) {
            navController.navigate(Screen.CheckoutGuest.route)
        }
        SecondaryButton(
            title = "Sign In to Checkout",
            icon = Icons.Default.Lock
        ) {
            navController.navigate(Screen.CheckoutSignIn.route)
        }
    }
}

// MARK: - Step 6: Checkout Options

@Composable
fun CheckoutGuestScreen(navController: NavController) {
    ScreenContainer(
        screenName = "CheckoutGuest",
        title = "Guest Checkout",
        subtitle = "Complete your purchase",
        step = 6,
        icon = Icons.Default.Person,
        color = Color(0xFF3F51B5) // Indigo
    ) {
        PrimaryButton(
            title = "Pay with Card",
            icon = Icons.Default.Done
        ) {
            navController.navigate(Screen.PaymentCard.route)
        }
        SecondaryButton(
            title = "Apple Pay",
            icon = Icons.Default.Phone
        ) {
            navController.navigate(Screen.PaymentApplePay.route)
        }
    }
}

@Composable
fun CheckoutSignInScreen(navController: NavController) {
    ScreenContainer(
        screenName = "CheckoutSignIn",
        title = "Member Checkout",
        subtitle = "Signed in as user@example.com",
        step = 6,
        icon = Icons.Default.Lock,
        color = Color(0xFF009688) // Teal
    ) {
        PrimaryButton(
            title = "Pay with Card",
            icon = Icons.Default.Done
        ) {
            navController.navigate(Screen.PaymentCard.route)
        }
        SecondaryButton(
            title = "PayPal",
            icon = Icons.Default.Send
        ) {
            navController.navigate(Screen.PaymentPayPal.route)
        }
    }
}

// MARK: - Step 6b: Payment Methods (all lead to confirmation)

@Composable
fun PaymentCardScreen(navController: NavController) {
    ScreenContainer(
        screenName = "PaymentCard",
        title = "Card Payment",
        subtitle = "Enter your card details",
        step = 6,
        icon = Icons.Default.Done,
        color = Color(0xFF2196F3) // Blue
    ) {
        PrimaryButton(
            title = "Complete Purchase",
            icon = Icons.Default.CheckCircle
        ) {
            navController.navigate(Screen.Confirmation.route)
        }
    }
}

@Composable
fun PaymentApplePayScreen(navController: NavController) {
    ScreenContainer(
        screenName = "PaymentApplePay",
        title = "Apple Pay",
        subtitle = "Confirm with Face ID",
        step = 6,
        icon = Icons.Default.Phone,
        color = Color.Black
    ) {
        PrimaryButton(
            title = "Complete Purchase",
            icon = Icons.Default.CheckCircle
        ) {
            navController.navigate(Screen.Confirmation.route)
        }
    }
}

@Composable
fun PaymentPayPalScreen(navController: NavController) {
    ScreenContainer(
        screenName = "PaymentPayPal",
        title = "PayPal",
        subtitle = "Redirecting to PayPal...",
        step = 6,
        icon = Icons.Default.Send,
        color = Color(0xFF2196F3) // Blue
    ) {
        PrimaryButton(
            title = "Complete Purchase",
            icon = Icons.Default.CheckCircle
        ) {
            navController.navigate(Screen.Confirmation.route)
        }
    }
}

// MARK: - Step 7: Confirmation (Final - All Paths Converge)

@Composable
fun ConfirmationScreen(navController: NavController) {
    ScreenContainer(
        screenName = "Confirmation",
        title = "Order Confirmed!",
        subtitle = "Thank you for your purchase.\nOrder #${Random.nextInt(10000, 99999)}",
        step = 7,
        icon = Icons.Default.CheckCircle,
        color = Color(0xFF4CAF50)
    ) {
        Button(
            onClick = {
                navController.navigate(Screen.Welcome.route) {
                    popUpTo(Screen.Welcome.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Text("Start New Journey", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
