package com.example.calculadora.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.calculadora.domain.model.CalculatorState
import com.example.calculadora.domain.util.Operation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CalculatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state

    fun onButtonClick(label: String) {
        val currentState = _state.value

        when (label) {
            in "0".."9", "." -> {
                val updatedText = if (currentState.operation == null) {
                    currentState.firstOperand + label
                } else {
                    currentState.secondOperand + label
                }
                _state.value = if (currentState.operation == null) {
                    currentState.copy(firstOperand = updatedText, displayText = updatedText)
                } else {
                    currentState.copy(secondOperand = updatedText, displayText = updatedText)
                }
            }
            "+", "-", "×", "÷" -> {
                val result = calculateResult(
                    currentState.firstOperand.toDoubleOrNull(),
                    currentState.secondOperand.toDoubleOrNull(),
                    currentState.operation
                )
                val newOperation = when (label) {
                    "+" -> Operation.ADD
                    "-" -> Operation.SUBTRACT
                    "×" -> Operation.MULTIPLY
                    "÷" -> Operation.DIVIDE
                    else -> null
                }
                _state.value = currentState.copy(
                    firstOperand = result,
                    secondOperand = "",
                    operation = newOperation,
                    displayText = ""
                )
            }
            "=" -> {
                val result = calculateResult(
                    currentState.firstOperand.toDoubleOrNull(),
                    currentState.secondOperand.toDoubleOrNull(),
                    currentState.operation
                )
                _state.value = currentState.copy(
                    firstOperand = result,
                    secondOperand = "",
                    operation = null,
                    displayText = result
                )
            }
            "C" -> {
                _state.value = CalculatorState()
            }
        }
    }


    private fun calculateResult(
        firstOperand: Double?,
        secondOperand: Double?,
        operation: Operation?
    ): String {
        if (firstOperand == null || operation == null) return "0"
        if (secondOperand == null) return formatNumber(firstOperand)

        val result = when (operation) {
            Operation.ADD -> firstOperand + secondOperand
            Operation.SUBTRACT -> firstOperand - secondOperand
            Operation.MULTIPLY -> firstOperand * secondOperand
            Operation.DIVIDE -> if (secondOperand != 0.0) {
                firstOperand / secondOperand
            } else {
                0.0 // Evita divisão por zero
            }
        }
        return formatNumber(result)
    }

    private fun formatNumber(value: Double): String {
        return if (value == value.toInt().toDouble()) {
            value.toInt().toString() // Remove ".0" se for um número inteiro
        } else {
            value.toString() // Mantém o formato de ponto flutuante se não for inteiro
        }
    }


}
