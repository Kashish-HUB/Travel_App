package com.example.travel_app

import java.io.Serializable

data class Hotel(
    val name: String,
    val city: String,
    val rating: Double,
    val price: Int,
    val imageRes: Int,             // drawable resource id
    val amenities: List<String>,
    val description: String
) : Serializable
