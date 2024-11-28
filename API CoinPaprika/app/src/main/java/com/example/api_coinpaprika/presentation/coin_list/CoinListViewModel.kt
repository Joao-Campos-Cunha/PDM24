import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_coinpaprika.data.remote.api.RetrofitInstance
import com.example.api_coinpaprika.data.repository.CoinRepositoryImpl
import com.example.api_coinpaprika.domain.model.Coin
import com.example.api_coinpaprika.domain.use_case.GetCoinsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

// UI para listar as moedas
class CoinListViewModel: ViewModel() {
    private val api = RetrofitInstance.api
    private val repository = CoinRepositoryImpl(api)
    private val getCoinsUseCase = GetCoinsUseCase(repository)

    val coins = MutableStateFlow<List<Coin>>(emptyList())

    fun fetchCoins() {
        viewModelScope.launch {
            try{
                coins.value = getCoinsUseCase()
            } catch (e: Exception) {
                coins.value = emptyList()
            }
        }
    }
}

