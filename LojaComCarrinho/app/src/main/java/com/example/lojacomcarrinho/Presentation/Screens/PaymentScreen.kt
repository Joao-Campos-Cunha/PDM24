package com.example.lojacomcarrinho.Presentation.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lojacomcarrinho.Presentation.ViewModels.PaymentViewModel
import com.example.lojacomcarrinho.Presentation.ViewModels.PaymentState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    totalPrice: Double,
    onPaymentSuccess: () -> Unit,
    onNavigateBack: () -> Unit,
    paymentViewModel: PaymentViewModel = viewModel()
) {
    var paymentMethod by remember { mutableStateOf("") }
    val paymentState by paymentViewModel.paymentState.observeAsState(PaymentState.Idle)

    LaunchedEffect(paymentState) {
        if (paymentState is PaymentState.Success) {
            onPaymentSuccess()
            paymentViewModel.resetPaymentState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pagamento") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Total a pagar: $totalPrice €",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            OutlinedTextField(
                value = paymentMethod,
                onValueChange = { paymentMethod = it },
                label = { Text("Método de Pagamento") },
                placeholder = { Text("Ex: Cartão de Crédito") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    paymentViewModel.processPayment(totalPrice, paymentMethod)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Pagar")
            }

            when (paymentState) {
                is PaymentState.Success -> {
                    Text(
                        text = "Pagamento realizado com sucesso!",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is PaymentState.Failure -> {
                    Text(
                        text = (paymentState as PaymentState.Failure).errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else -> {}
            }
        }
    }
}
