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
                if (currentState.isResultDisplayed) {
                    // Reinicia a operação após exibir o resultado
                    _state.value = CalculatorState(
                        firstOperand = label,
                        displayText = label
                    )
                } else {
                    val updatedText = if (currentState.operation == null) {
                        currentState.firstOperand + label
                    } else {
                        currentState.secondOperand + label
                    }

                    if (updatedText.length > 9) return // Limita o visor a 9 dígitos

                    _state.value = if (currentState.operation == null) {
                        currentState.copy(
                            firstOperand = updatedText,
                            displayText = updatedText
                        )
                    } else {
                        currentState.copy(
                            secondOperand = updatedText,
                            displayText = updatedText
                        )
                    }
                }
            }

            "+", "-", "×", "÷" -> {
                if (currentState.isResultDisplayed) {
                    _state.value = CalculatorState(
                        firstOperand = currentState.displayText,
                        operation = parseOperation(label)
                    )
                } else if (currentState.operation != null && currentState.secondOperand.isNotEmpty()) {
                    // Calcula o resultado intermediário antes de definir a próxima operação
                    val result = calculateResult(
                        currentState.firstOperand.toDoubleOrNull(),
                        currentState.secondOperand.toDoubleOrNull(),
                        currentState.operation
                    )
                    _state.value = CalculatorState(
                        firstOperand = result,
                        operation = parseOperation(label)
                    )
                } else if (currentState.firstOperand.isNotEmpty()) {
                    _state.value = currentState.copy(
                        operation = parseOperation(label),
                        displayText = "" // Limpa o visor ao iniciar uma nova operação
                    )
                }
            }

            "=" -> {
                val result = calculateResult(
                    currentState.firstOperand.toDoubleOrNull(),
                    currentState.secondOperand.toDoubleOrNull(),
                    currentState.operation
                )
                _state.value = CalculatorState(
                    firstOperand = result,
                    displayText = result,
                    isResultDisplayed = true
                )
            }

            "CE" -> {
                _state.value = CalculatorState()
            }

            "√" -> { // Calcula a raiz quadrada
                val currentNumber = currentState.displayText.toDoubleOrNull()
                if (currentNumber != null && currentNumber >= 0) {
                    val result = kotlin.math.sqrt(currentNumber)
                    _state.value = CalculatorState(
                        firstOperand = result.toString(),
                        displayText = formatNumber(result),
                        isResultDisplayed = true
                    )
                }
            }

            "+/-" -> { // Inverte o sinal do número no visor
                val currentNumber = currentState.displayText.toDoubleOrNull()
                if (currentNumber != null) {
                    val result = -currentNumber
                    _state.value = CalculatorState(
                        firstOperand = result.toString(),
                        displayText = formatNumber(result),
                        isResultDisplayed = true
                    )
                }
            }

            "%" -> { // Calcula porcentagem
                val currentNumber = currentState.displayText.toDoubleOrNull()
                if (currentNumber != null) {
                    val result = currentNumber / 100
                    _state.value = CalculatorState(
                        firstOperand = result.toString(),
                        displayText = formatNumber(result),
                        isResultDisplayed = true
                    )
                }
            }
        }
    }

    private fun parseOperation(label: String): Operation? {
        return when (label) {
            "+" -> Operation.ADD
            "-" -> Operation.SUBTRACT
            "×" -> Operation.MULTIPLY
            "÷" -> Operation.DIVIDE
            else -> null
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
