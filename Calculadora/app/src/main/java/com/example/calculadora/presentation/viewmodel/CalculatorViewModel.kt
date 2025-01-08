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

                    if (isValidLength(updatedText)) {
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
            }

            "+", "-", "×", "÷" -> {
                if (currentState.isResultDisplayed) {
                    _state.value = CalculatorState(
                        firstOperand = currentState.displayText,
                        operation = parseOperation(label)
                    )
                } else if (currentState.operation != null && currentState.secondOperand.isNotEmpty()) {
                    val result = calculateResult(
                        currentState.firstOperand.toDoubleOrNull(),
                        currentState.secondOperand.toDoubleOrNull(),
                        currentState.operation
                    )
                    _state.value = CalculatorState(
                        firstOperand = result.toString(),
                        operation = parseOperation(label)
                    )
                } else if (currentState.firstOperand.isNotEmpty()) {
                    _state.value = currentState.copy(
                        operation = parseOperation(label),
                        displayText = ""
                    )
                }
            }

            "=" -> {
                val result = calculateResult(
                    currentState.firstOperand.toDoubleOrNull(),
                    currentState.secondOperand.toDoubleOrNull(),
                    currentState.operation
                )
                _state.value = currentState.copy(
                    firstOperand = result.toString(),
                    secondOperand = "",
                    operation = null,
                    displayText = formatNumber(result),
                    isResultDisplayed = true
                )
            }

            "CE" -> {
                _state.value = CalculatorState()
            }

            "√" -> {
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

            "+/-" -> {
                val currentNumber = currentState.displayText.toDoubleOrNull()
                if (currentNumber != null) {
                    val result = -currentNumber
                    val formattedResult = formatNumber(result)
                    if (isValidLength(formattedResult)) {
                        _state.value = currentState.copy(
                            firstOperand = result.toString(),
                            displayText = formattedResult,
                            isResultDisplayed = true
                        )
                    }
                }
            }

            "%" -> {
                val currentNumber = currentState.displayText.toDoubleOrNull()
                if (currentNumber != null) {
                    val result = currentNumber / 100
                    val formattedResult = formatNumber(result)
                    if (isValidLength(formattedResult)) {
                        _state.value = currentState.copy(
                            firstOperand = result.toString(),
                            displayText = formattedResult,
                            isResultDisplayed = true
                        )
                    }
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
    ): Double {
        if (firstOperand == null || operation == null) return 0.0
        if (secondOperand == null) return firstOperand

        return when (operation) {
            Operation.ADD -> firstOperand + secondOperand
            Operation.SUBTRACT -> firstOperand - secondOperand
            Operation.MULTIPLY -> firstOperand * secondOperand
            Operation.DIVIDE -> if (secondOperand != 0.0) {
                firstOperand / secondOperand
            } else {
                0.0 // Evita divisão por zero
            }
        }
    }

    private fun formatNumber(value: Double): String {
        val formatted = if (value == value.toInt().toDouble()) {
            value.toInt().toString()
        } else {
            value.toString()
        }

        return if (formatted.length > 9) {
            "%e".format(value) // Notação científica se exceder 9 caracteres
        } else {
            formatted
        }
    }

    private fun isValidLength(value: String): Boolean {
        return value.replace("-", "").length <= 10 // Ignora o sinal negativo ao contar
    }
}
