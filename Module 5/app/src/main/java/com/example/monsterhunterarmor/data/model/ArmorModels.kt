package com.example.monsterhunterarmor.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class ArmorResponse(
    val id: Int,
    val name: String,
    val rank: String,
    val type: String,
    val assets: ArmorAssets? = null,
    val defense: ArmorDefense,
    val resistances: ArmorResistances,
    val skills: List<ArmorSkill>,
    val slots: List<ArmorSlot>
)

@Serializable
data class ArmorAssets(
    val imageMale: String? = null,
    val imageFemale: String? = null
)

@Parcelize
@Serializable
data class ArmorDefense(
    val base: Int,
    val max: Int,
    val augmented: Int
) : Parcelable

@Parcelize
@Serializable
data class ArmorResistances(
    val fire: Int,
    val water: Int,
    val ice: Int,
    val thunder: Int,
    val dragon: Int
) : Parcelable

@Parcelize
@Serializable
data class ArmorSkill(
    val id: Int,
    val level: Int,
    val skillName: String,
    val description: String
) : Parcelable

@Serializable
data class ArmorSlot(
    val rank: Int
)