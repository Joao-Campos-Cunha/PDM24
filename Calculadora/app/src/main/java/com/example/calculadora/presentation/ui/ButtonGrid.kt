package com.example.calculadora.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonGrid(onButtonClick: (String) -> Unit) {
    val buttons = listOf(
        listOf("7", "8", "9", "÷"),
        listOf("4", "5", "6", "×"),
        listOf("1", "2", "3", "-"),
        listOf("C", "0", "=", "+")
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp) // Espaçamento entre as linhas
    ) {
        buttons.forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp), // Espaçamento horizontal
                modifier = Modifier.fillMaxWidth()
            ) {
                row.forEach { label ->
                    CalculatorButton(
                        label = label,
                        onClick = { onButtonClick(label) },
                        modifier = Modifier.weight(1f) // Adicione peso para cada botão
                    )
                }
            }
        }
    }
}