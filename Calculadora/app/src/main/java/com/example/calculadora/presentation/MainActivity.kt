package com.example.calculadora.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.calculadora.presentation.ui.CalculatorScreen
import com.example.calculadora.presentation.viewmodel.CalculatorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = CalculatorViewModel()
            CalculatorScreen(viewModel = viewModel)
        }
    }
}


