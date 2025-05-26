package com.example.tugasmobileapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Response
import android.util.Log

data class Item(val id: Int, val name: String)
data class ApiResponse(val message: String, val items: List<Item>)

interface ApiService {
    @GET("/data")
    suspend fun getData(): Response<ApiResponse>
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val coroutineScope = rememberCoroutineScope()
    var items by remember { mutableStateOf<List<Item>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            isLoading = true
            coroutineScope.launch {
                try {
                    val apiService = Retrofit.Builder()
                        .baseUrl("https://tugasrahasiamobile-api.free.beeceptor.com ")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(ApiService::class.java)

                    val response = apiService.getData()
                    if (response.isSuccessful) {
                        items = response.body()?.items ?: emptyList()
                    } else {
                        Log.e("API", "Response gagal: ${response.code()}")
                    }
                } catch (e: Exception) {
                    Log.e("API", "Error: ${e.message}", e)
                } finally {
                    isLoading = false
                }
            }
        }) {
            Text("Ambil Data")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = items, itemContent = { item ->
                Text(text = "${item.id}. ${item.name}", modifier = Modifier.padding(8.dp))
            })
        }
    }
}