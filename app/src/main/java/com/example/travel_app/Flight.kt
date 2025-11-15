package com.example.travel_app

import java.io.Serializable

data class Flight(
    val id: String,
    val airline: String,
    val fromCity: String,
    val toCity: String,
    val departTime: String,
    val arriveTime: String,
    val duration: String,
    val stops: Int,
    val price: Int
) : Serializable
