package com.example.lojacomcarrinho.Presentation.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lojacomcarrinho.Domain.model.CartItem
import com.example.lojacomcarrinho.Presentation.ViewModels.CartViewModel


@Composable
fun CartScreen(
    userId: String,
    cartViewModel: CartViewModel = viewModel() // ViewModel correto
) {
    val cartItems by cartViewModel.cartItems.observeAsState(emptyList())
    val totalPrice by cartViewModel.totalPrice.observeAsState(0.0)

    LaunchedEffect(Unit) {
        cartViewModel.fetchCart(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Carrinho de Compras") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(cartItems) { cartItem ->
                    CartItemRow(
                        cartItem = cartItem,
                        onUpdateQuantity = { newQuantity ->
                            cartViewModel.updateQuantity(userId, cartItem, newQuantity)
                        },
                        onRemoveItem = {
                            cartViewModel.removeItem(userId, cartItem)
                        }
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "Preço Total: $totalPrice €",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun CartItemRow(
    cartItem: CartItem,
    onUpdateQuantity: (Int) -> Unit,
    onRemoveItem: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = cartItem.productId)

        Row {
            IconButton(onClick = { onUpdateQuantity(cartItem.quantity - 1) }) {
                Icon(Icons.Default.Remove, contentDescription = "Diminuir quantidade")
            }

            Text(text = "${cartItem.quantity}", modifier = Modifier.padding(horizontal = 8.dp))

            IconButton(onClick = { onUpdateQuantity(cartItem.quantity + 1) }) {
                Icon(Icons.Default.Add, contentDescription = "Aumentar quantidade")
            }
        }

        IconButton(onClick = onRemoveItem) {
            Icon(Icons.Default.Delete, contentDescription = "Remover")
        }
    }
}
