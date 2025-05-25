package com.example.wuwalist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Character(
    val name: String,
    val link: String,
    val description: String,
    val photo: Int
):Parcelable