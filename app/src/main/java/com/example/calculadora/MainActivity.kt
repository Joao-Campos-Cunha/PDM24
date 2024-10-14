package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Calculadora()
                    }
                }
            }
        }
    }
}


@Composable
fun Calculadora() {
    var displayText by remember { mutableStateOf("0") } // Variável de estado para o visor

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Gray)
    ) {
        // Visor da calculadora
        Text(
            text = displayText,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(40.dp)
                .background(Color.LightGray)
                .border(2.dp, Color.Black),
            fontSize = 32.sp // Tamanho da fonte do visor


        )

        Spacer(modifier = Modifier.height(16.dp)) // Espaçamento entre o visor e os botões

        // Primeira linha de botões
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { displayText = "MRC" }, modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(Color.Black)) {
                Text(text = "MRC")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "M-" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.Black)) {
                Text(text = "M-")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "M+" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.Black)) {
                Text(text = "M+")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "On/C" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.Red)) {
                Text(text = "On/C")
            }
        }

        // Segunda linha de botões
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { displayText = "sqr" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.Black)) {
                Text(text = "sqr")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "%" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.Black)) {
                Text(text = "%")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "+/-" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.Black)) {
                Text(text = "+/-")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "CE" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.Red)) {
                Text(text = "CE")
            }
        }

        // Terceira linha de botões
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { displayText = "7" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "7")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "8" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "8")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "9" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "9")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "/" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "/")
            }
        }

        // Quarta linha de botões
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { displayText = "4" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "4")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "5" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "5")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "6" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "6")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "*" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "*")
            }
        }

        // Quinta linha de botões
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { displayText = "1" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "1")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "2" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "2")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "3" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "3")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "-" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "-")
            }
        }
        // Quinta linha de botões
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { displayText = "0" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "0")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "." }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = ".")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "=" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "=")
            }

            Spacer(modifier = Modifier.width(4.dp))

            Button(onClick = { displayText = "+" }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color.DarkGray)) {
                Text(text = "+")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculadoraPreview() {
    CalculadoraTheme {
        Calculadora()
    }
}
