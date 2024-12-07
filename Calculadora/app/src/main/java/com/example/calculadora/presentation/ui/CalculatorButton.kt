package com.example.calculadora.presentation.ui

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorButton(label: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(4.dp) // Espaçamento ao redor do botão
            .aspectRatio(1f), // Proporção quadrada
        shape = RoundedCornerShape(8.dp), // Botões arredondados
        colors = ButtonDefaults.buttonColors(
            containerColor = when (label) {
                "CE", "√", "%", "+/-" -> Color.Red.copy(alpha = 0.9f) // Funções de memória e CE
                "÷", "×", "-", "+", "=" -> Color.Black.copy(alpha = 0.8f) // Operadores
                "." , "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> Color.Gray // Ponto decimal
                else -> Color.LightGray // Botões numéricos e ponto
            },
            contentColor = Color.White // Cor do texto
        )
    ) {
        Text(
            text = label,
            fontSize = 20.sp, // Tamanho do texto
            fontWeight = FontWeight.Bold // Texto em negrito
        )
    }
}
