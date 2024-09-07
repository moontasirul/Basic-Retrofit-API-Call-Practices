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


    /**
     * It's designed to hold a list of Product objects and is initialized with an empty list.
     * State flows are observable data holders that emit updates
     * to their subscribers whenever the value changes.
     */
    private val _products = MutableStateFlow<List<Product>>(emptyList())

    /**
     * Creates a public, read-only state flow
     * named products by transforming the mutable _products using asStateFlow().
     * This exposes the product list to observers
     */
    val products = _products.asStateFlow()


    /**
     * Channels are used for communication between coroutines.
     * This channel is designed to send a boolean value,
     * likely indicating whether or not to display an error toast message.
     */
    private val _showErrorToastChannel = Channel<Boolean>()

    /**
     * The receiveAsFlow() function converts the channel into a flow,
     * allowing observers to collect values emitted by the channel.
     */
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()


    /**
     * Initialization block of the ViewModel.
     * Code within this block is executed when the ViewModel is created.
     */
    init {
        viewModelScope.launch {
            // likely returns a flow that emits the result of fetching a list of products.
            productRepository.getProductList().collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }

                    is Result.Success -> {
                        result.data?.let { product ->
                            // _products state flow is updated with the new product list using the update function.
                            // This will notify any observers of the state flow that the product list has changed.
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