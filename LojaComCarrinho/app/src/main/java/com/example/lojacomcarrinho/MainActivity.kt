package com.example.lojacomcarrinho

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lojacomcarrinho.Data.repository.AuthRepository
import com.example.lojacomcarrinho.Data.repository.CartRepository
import com.example.lojacomcarrinho.Data.repository.ProductRepository
import com.example.lojacomcarrinho.Domain.model.CartItem
import com.example.lojacomcarrinho.Domain.usecase.FetchProductsUseCase
import com.example.lojacomcarrinho.Domain.usecase.LoginUseCase
import com.example.lojacomcarrinho.Domain.usecase.ManageCartUseCase
import com.example.lojacomcarrinho.Domain.usecase.RegisterUseCase
import com.example.lojacomcarrinho.Presentation.Screens.*
import com.example.lojacomcarrinho.Presentation.ViewModels.*
import com.example.lojacomcarrinho.ui.theme.LojaComCarrinhoTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar Firebase
        FirebaseApp.initializeApp(this)

        setContent {
            LojaComCarrinhoTheme {
                val navController = rememberNavController()
                val viewModelFactory = remember { provideViewModelFactory() }
                AppNavHost(navController = navController, viewModelFactory = viewModelFactory)
            }
        }
    }

    private fun provideViewModelFactory(): ViewModelFactory {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        val authRepository = AuthRepository(firebaseAuth)
        val cartRepository = CartRepository(firestore)
        val productRepository = ProductRepository(firestore)

        val registerUseCase = RegisterUseCase(authRepository)
        val loginUseCase = LoginUseCase(authRepository)
        val fetchProductsUseCase = FetchProductsUseCase(productRepository)
        val manageCartUseCase = ManageCartUseCase(cartRepository)

        return ViewModelFactory(
            registerUseCase = registerUseCase,
            loginUseCase = loginUseCase,
            fetchProductsUseCase = fetchProductsUseCase,
            manageCartUseCase = manageCartUseCase
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModelFactory: ViewModelFactory
) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            val authViewModel = viewModelFactory.provideAuthViewModel()
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToRegister = { navController.navigate("register") },
                onLoginSuccess = { navController.navigate("products") }
            )
        }

        composable("register") {
            val registerViewModel = viewModelFactory.provideRegisterViewModel()
            RegisterScreen(
                registerViewModel = registerViewModel,
                onNavigateBack = { navController.popBackStack() },
                onRegisterSuccess = { navController.navigate("login") }
            )
        }

        composable("products") {
            val productListViewModel = viewModelFactory.provideProductListViewModel()
            ProductListScreen(
                productListViewModel = productListViewModel,
                onNavigateToCart = { navController.navigate("cart") },
                onNavigateToPayment = { navController.navigate("payment") },
                onProductSelected = { product ->
                    // Exemplo: Adicionar produto ao carrinho
                    val cartViewModel = viewModelFactory.provideCartViewModel()
                    cartViewModel.addItem(
                        userId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
                        cartItem = CartItem(productId = product.id, quantity = 1, price = product.price)
                    )
                }
            )
        }

        composable("cart") {
            val cartViewModel = viewModelFactory.provideCartViewModel()
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            CartScreen(
                userId = currentUserId,
                cartViewModel = cartViewModel,
                onNavigateToPayment = { navController.navigate("payment") },

            )
        }

        composable("payment") {
            PaymentScreen(
                onPaymentSuccess = { navController.navigate("products") },
                onNavigateBack = { navController.popBackStack() },
                totalPrice = TODO(),
                paymentViewModel = TODO()
            )
        }
    }
}

class ViewModelFactory(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val fetchProductsUseCase: FetchProductsUseCase,
    private val manageCartUseCase: ManageCartUseCase
) {
    fun provideRegisterViewModel(): RegisterViewModel {
        return RegisterViewModel(registerUseCase)
    }

    fun provideAuthViewModel(): AuthViewModel {
        return AuthViewModel(loginUseCase)
    }

    fun provideProductListViewModel(): ProductViewModel {
        return ProductViewModel(fetchProductsUseCase)
    }

    fun provideCartViewModel(): CartViewModel {
        return CartViewModel(manageCartUseCase)
    }
}
