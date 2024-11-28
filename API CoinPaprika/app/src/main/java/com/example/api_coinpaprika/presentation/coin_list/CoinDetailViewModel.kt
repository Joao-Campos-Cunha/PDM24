
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api_coinpaprika.data.remote.api.RetrofitInstance
import com.example.api_coinpaprika.data.repository.CoinRepositoryImpl
import com.example.api_coinpaprika.domain.model.CoinDetail
import com.example.api_coinpaprika.domain.use_case.GetCoinDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

// UI para listar uma moeda e os detalhes especificos
class CoinDetailViewModel: ViewModel() {  
    private val api = RetrofitInstance.api
    private val repository = CoinRepositoryImpl(api)
    private val getCoinDetailUseCase = GetCoinDetailUseCase(repository)

    val coinDetail = MutableStateFlow<CoinDetail?>(null)

    fun fetchCoinDetail(coinId: String){
        viewModelScope.launch {
            try{
                coinDetail.value = getCoinDetailUseCase(coinId)
            } catch (e: Exception){
                coinDetail.value = null
            }
        }
    }
}

