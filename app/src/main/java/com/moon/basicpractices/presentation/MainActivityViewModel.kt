package com.moon.basicpractices.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.basicpractices.data.ProductRepository
import com.moon.basicpractices.data.Result
import com.moon.basicpractices.data.model.Product
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {


    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()


    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()


    init {
        viewModelScope.launch {
            productRepository.getProductList().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }

                    is Result.Success -> {
                        result.data?.let { product ->
                            _products.update {
                                product
                            }
                        }
                    }
                }
            }
        }
    }
}