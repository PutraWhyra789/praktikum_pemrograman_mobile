package com.example.wuwalist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Character(
    val name: String,
    val photo: Int,
    val link: String,
    val description: String,
    val profile: String
):Parcelable
