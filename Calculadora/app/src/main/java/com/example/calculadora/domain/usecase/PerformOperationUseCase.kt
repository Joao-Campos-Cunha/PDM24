package com.example.calculadora.domain.usecase

import com.example.calculadora.domain.util.Operation

class PerformOperationUseCase {
    fun execute(operand1: Double, operand2: Double, operation: Operation): Double {
        return when (operation) {
            Operation.ADD -> operand1 + operand2
            Operation.SUBTRACT -> operand1 - operand2
            Operation.MULTIPLY -> operand1 * operand2
            Operation.DIVIDE -> operand1 / operand2
        }
    }
}
