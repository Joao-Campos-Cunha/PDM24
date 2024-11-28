package com.example.api_coinpaprika

import CoinListViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.api_coinpaprika.domain.model.Coin


@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = viewModel(),
    onCoinClick: (String) -> Unit
) {
    val coins by viewModel.coins.collectAsState()
    val headerCustomColor = Color(0xFF0061b0)

    LaunchedEffect(Unit) {
        viewModel.fetchCoins()
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .background(color = headerCustomColor)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Cryptocurrencies List",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall)
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(coins) { coin ->
                CoinItem(coin = coin, onClick = { onCoinClick(coin.id)})
            }
        }
    }
}

@Composable
fun CoinItem(coin: Coin, onClick: () -> Unit){

    var isClicked by remember { mutableStateOf(false) }
    val boxCustomColor = Color(0xFF0061b0)

    // Crypto list component
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        // Whole row
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank circle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color = boxCustomColor),
                contentAlignment = Alignment.Center
            ) {
                // Rank circle text
                Text(
                    text = coin.rank,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column (
                modifier = Modifier
                    .weight(1f)
                    .clickable { onClick() }
            )
            {
                // Crypto Name
                Text(
                    text = coin.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                // Crypto Symbol
                Text(
                    text = coin.symbol,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }


//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(vertical = 8.dp)
//            .clickable { onClick() }
//            .background(color = Color.Blue, shape = RectangleShape)
//    ) {
//        Text(text = coin.name, style = MaterialTheme.typography.titleMedium)
//        Text(text = coin.symbol, style = MaterialTheme.typography.bodySmall)
//    }
}