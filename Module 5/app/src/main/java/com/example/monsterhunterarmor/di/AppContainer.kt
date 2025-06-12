package com.example.monsterhunterarmor.di

import android.content.Context
import com.example.monsterhunterarmor.data.local.ArmorDatabase
import com.example.monsterhunterarmor.data.remote.ArmorApiService
import com.example.monsterhunterarmor.repository.ArmorRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class AppContainer(private val context: Context) {

    private val json = Json { ignoreUnknownKeys = true }

    private val loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mhw-db.com/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(client)
        .build()

    private val apiService: ArmorApiService by lazy {
        retrofit.create(ArmorApiService::class.java)
    }

    private val armorDb: ArmorDatabase by lazy {
        ArmorDatabase.getDatabase(context)
    }

    val armorRepository: ArmorRepository by lazy {
        ArmorRepository(apiService, armorDb.armorDao())
    }
}