package com.example.calculadora.domain.model
import com.example.calculadora.domain.util.Operation

data class CalculatorState(
    val firstOperand: String = "",
    val secondOperand: String = "",
    val operation: Operation? = null,
    val displayText: String = "",
    val operationText: String = "", // Armazena a operação completa para exibição
    val isResultDisplayed: Boolean = false
)

