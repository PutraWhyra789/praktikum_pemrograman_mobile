package com.example.monsterhunterarmor.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArmorDao {
    @Query("SELECT * FROM armor")
    fun getAllArmor(): Flow<List<ArmorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(armorList: List<ArmorEntity>)

    @Query("DELETE FROM armor")
    suspend fun deleteAll()
}