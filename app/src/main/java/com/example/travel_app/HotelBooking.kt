package com.example.travel_app

import java.io.Serializable

data class HotelBooking(
    val hotelName: String,
    val price: Int,
    val checkIn: String,
    val checkOut: String,
    val guests: Int
) : Serializable
