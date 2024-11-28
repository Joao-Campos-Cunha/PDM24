package com.example.api_coinpaprika

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            RegistrationForm()
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "coin_limhst") {
        composable("coin_list") {
            CoinListScreen(onCoinClick = { coinId ->
                navController.navigate("coin_detail/$coinId")
            })
        }
        composable("coin_detail/{coinId}") { backStackEntry ->
            val coinId = backStackEntry.arguments?.getString("coinId") ?: return@composable
            CoinDetailScreen(coinId = coinId)
        }
    }
}

// These are examples of Compose components that could be used
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var agreeToTerms by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Registration Form",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = agreeToTerms,
                onCheckedChange = { agreeToTerms = it }
            )
            Text("I agree to the terms and conditions")
        }

        Button(
            onClick = { /* Handle registration */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && agreeToTerms
        ) {
            Text("Register")
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        Text(
            text = "Other UI Components",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { /* Handle click */ }) {
                Text("Primary")
            }
            OutlinedButton(onClick = { /* Handle click */ }) {
                Text("Secondary")
            }
            TextButton(onClick = { /* Handle click */ }) {
                Text("Text Button")
            }
        }

        Switch(
            checked = agreeToTerms,
            onCheckedChange = { agreeToTerms = it }
        )

        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())

        CircularProgressIndicator()

        Slider(
            value = 0.5f,
            onValueChange = { /* Handle value change */ },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppNavigation()
}
