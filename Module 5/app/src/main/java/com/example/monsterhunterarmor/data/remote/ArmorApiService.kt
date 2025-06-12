package com.example.monsterhunterarmor.data.remote

import com.example.monsterhunterarmor.data.model.ArmorResponse
import retrofit2.http.GET

interface ArmorApiService {
    @GET("armor")
    suspend fun getArmor(): List<ArmorResponse>
}