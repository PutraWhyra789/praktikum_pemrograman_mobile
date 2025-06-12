package com.example.monsterhunterarmor.repository

import com.example.monsterhunterarmor.data.local.ArmorDao
import com.example.monsterhunterarmor.data.local.ArmorEntity
import com.example.monsterhunterarmor.data.remote.ApiResponse
import com.example.monsterhunterarmor.data.remote.ArmorApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class ArmorRepository(
    private val apiService: ArmorApiService,
    private val armorDao: ArmorDao
) {
    fun getArmorList(): Flow<ApiResponse<Flow<List<ArmorEntity>>>> = flow {
        emit(ApiResponse.Loading)
        val localData = armorDao.getAllArmor()
        emit(ApiResponse.Success(localData))

        try {
            val response = apiService.getArmor()
            val armorEntities = response.map { armorResponse ->
                ArmorEntity(
                    id = armorResponse.id,
                    name = armorResponse.name,
                    rank = armorResponse.rank,
                    type = armorResponse.type,
                    imageUrl = armorResponse.assets?.imageMale ?: armorResponse.assets?.imageFemale,
                    defense = armorResponse.defense,
                    resistances = armorResponse.resistances,
                    skills = armorResponse.skills
                )
            }
            armorDao.deleteAll()
            armorDao.insertAll(armorEntities)
        } catch (e: Exception) {
            emit(ApiResponse.Error("Failed to fetch from network: ${e.message}"))
            e.printStackTrace()
        }
    }
}