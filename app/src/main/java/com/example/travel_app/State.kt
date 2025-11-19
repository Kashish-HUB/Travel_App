package com.example.travel_app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class State(
    val name: String,
    val description: String,
    val popularPlaces: List<String>,
    val famousDishes: List<String>,
    val images: List<Int>,
    val mapQuery: String
) : Parcelable
