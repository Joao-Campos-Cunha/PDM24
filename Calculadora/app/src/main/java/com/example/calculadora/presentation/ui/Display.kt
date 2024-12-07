package com.example.calculadora.presentation.ui

import android.R.attr.fontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Display(text: String, operationText: String) {
    val displayContent = if (operationText.isNotEmpty()) operationText else text
    Text(
        text = displayContent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xffadba99))
            .border(2.dp, Color.Black)
            .padding(16.dp),
        fontSize = 35.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.End,
        style = MaterialTheme.typography.headlineLarge

    )
}

