package com.example.monsterhunterarmor.data.local

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.monsterhunterarmor.data.model.ArmorDefense
import com.example.monsterhunterarmor.data.model.ArmorResistances
import com.example.monsterhunterarmor.data.model.ArmorSkill
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "armor")
data class ArmorEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val rank: String,
    val type: String,
    val imageUrl: String?,

    @Embedded
    val defense: ArmorDefense,

    @Embedded
    val resistances: ArmorResistances,

    val skills: List<ArmorSkill>

) : Parcelable