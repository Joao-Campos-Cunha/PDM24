package com.example.lojacomcarrinho.Presentation.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lojacomcarrinho.Domain.model.Product
import com.example.lojacomcarrinho.Presentation.ViewModels.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productListViewModel: ProductViewModel,
    onNavigateToCart: () -> Unit,
    onNavigateToPayment: () -> Unit,
    onProductSelected: (Product) -> Unit
) {
    val productList by productListViewModel.productList.observeAsState(emptyList())
    val isLoading by productListViewModel.isLoading.observeAsState(false)
    val errorMessage by productListViewModel.errorMessage.observeAsState(null)

    LaunchedEffect(Unit) {
        productListViewModel.fetchProducts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Produtos") },
                navigationIcon = {
                    IconButton(onClick = { onNavigateToCart() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                errorMessage != null -> {
                    ErrorView(
                        message = errorMessage!!,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    ProductList(
                        productList = productList,
                        onProductSelected = onProductSelected,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ActionButtons(
                        onNavigateToCart = onNavigateToCart,
                        onNavigateToPayment = onNavigateToPayment
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorView(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ProductList(
    productList: List<Product>,
    onProductSelected: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp)
    ) {
        items(productList) { product ->
            ProductItem(product = product, onProductSelected = onProductSelected)
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    onProductSelected: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onProductSelected(product) },
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Preço: ${product.price} €",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun ActionButtons(
    onNavigateToCart: () -> Unit,
    onNavigateToPayment: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = onNavigateToCart) {
            Text("Carrinho")
        }
        Button(onClick = onNavigateToPayment) {
            Text("Pagamento")
        }
    }
}
