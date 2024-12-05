package com.example.calculadora.domain.model
import com.example.calculadora.domain.util.Operation

data class CalculatorState(
    val displayText: String = "0",
    val firstOperand: String = "",
    val secondOperand: String = "",
    val operation: Operation? = null
)
