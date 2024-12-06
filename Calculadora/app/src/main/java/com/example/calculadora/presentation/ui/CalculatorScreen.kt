package com.example.calculadora.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.calculadora.presentation.viewmodel.CalculatorViewModel

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    val state by viewModel.state.collectAsState()

    Column {
        Display(
            text = state.displayText,
            operationText = state.operationText // Passa o texto da operação
        )
        ButtonGrid { label -> viewModel.onButtonClick(label) }
    }
}


