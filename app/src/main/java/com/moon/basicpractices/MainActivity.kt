package com.moon.basicpractices

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.moon.basicpractices.data.ProductRepositoryImp
import com.moon.basicpractices.data.model.Product
import com.moon.basicpractices.network.RetrofitInstance
import com.moon.basicpractices.presentation.MainActivityViewModel
import com.moon.basicpractices.ui.theme.BasicRetrofitAPICallPracticesTheme
import kotlinx.coroutines.flow.collectLatest


class MainActivity : ComponentActivity() {

    /**
     *  Creates an instance of the MainActivityViewModel using the viewModels delegate,
     * passing in a custom ViewModelProvider.Factory.
     */
    private val mainActivityViewModel by viewModels<MainActivityViewModel>(factoryProducer = {
        // The factoryProducer lambda is used to create a ViewModelProvider.Factory
        // that will be used to create the MainActivityViewModel instance.
        object : ViewModelProvider.Factory {
            // The create() method is called by the ViewModelProvider to create a new
            // instance of the MainActivityViewModel.
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                // Create an instance of the ProductRepositoryImp using the RetrofitInstance.
                val productRepository = ProductRepositoryImp(RetrofitInstance.productAPI)

                // Create an instance of the MainActivityViewModel using the ProductRepositoryImp.
                return MainActivityViewModel(productRepository) as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicRetrofitAPICallPracticesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val productList = mainActivityViewModel.products.collectAsState().value
                    val context = LocalContext.current
                    // Observes the showErrorToastChannel from
                    // the mainActivityViewModel and displays a Toast message if the value is true.
                    LaunchedEffect(key1 = mainActivityViewModel.showErrorToastChannel) {
                        // Collects the latest value from the showErrorToastChannel.
                        mainActivityViewModel.showErrorToastChannel.collectLatest { show ->
                            // If the value is true, display a Toast message.
                            if (show) {
                                Toast.makeText(context, "Error Found", Toast.LENGTH_LONG).show()
                            }
                        }
                    }


                    if (productList.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(productList.size) { index ->
                                Spacer(modifier = Modifier.height(16.dp))
                                Product(productList[index])

                                if (index == productList.size - 1) {
                                    Spacer(modifier = Modifier.height(32.dp)) // Add extra spacer at the end
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Product(product: Product) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(product.thumbnail)
            .size(Size.ORIGINAL).build()
    ).state

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .height(300.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {

        if (imageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }


        if (imageState is AsyncImagePainter.State.Success) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                painter = imageState.painter, contentDescription = product.title,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "${product.title} -- Price: ${product.price}",
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(16.dp, bottom = 16.dp),
            text = product.description,
            fontSize = 13.sp
        )
    }
}