package com.example.lojacomcarrinho.Presentation.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lojacomcarrinho.Domain.model.CartItem
import com.example.lojacomcarrinho.Presentation.ViewModels.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    userId: String,
    cartViewModel: CartViewModel,
    onNavigateToPayment: () -> Unit
) {
    // Observa os estados do ViewModel
    val cartItems by cartViewModel.cartItems.observeAsState(emptyList())
    val totalPrice by cartViewModel.totalPrice.observeAsState(0.0)
    val errorMessage by cartViewModel.error.observeAsState()

    // Carrega o carrinho assim que o Composable for criado
    LaunchedEffect(Unit) {
        cartViewModel.fetchCart(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrinho de Compras (${cartItems.size} itens)") },
                actions = {
                    IconButton(onClick = { cartViewModel.fetchCart(userId) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Limpar Carrinho")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToPayment,
                content = { Text("Pagar") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "Erro desconhecido",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "O carrinho está vazio.")
                }
            } else {
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
                    text = "Preço Total: %.2f €".format(totalPrice),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.End)
                )
            }
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Exibe as informações do item no carrinho
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Produto: ${cartItem.productId}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Preço Unitário: %.2f €".format(cartItem.price),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onUpdateQuantity(cartItem.quantity - 1) }) {
                Icon(Icons.Default.Remove, contentDescription = "Diminuir quantidade")
            }

            Text(
                text = "${cartItem.quantity}",
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            IconButton(onClick = { onUpdateQuantity(cartItem.quantity + 1) }) {
                Icon(Icons.Default.Add, contentDescription = "Aumentar quantidade")
            }
        }

        IconButton(onClick = onRemoveItem) {
            Icon(Icons.Default.Delete, contentDescription = "Remover")
        }
    }
}
