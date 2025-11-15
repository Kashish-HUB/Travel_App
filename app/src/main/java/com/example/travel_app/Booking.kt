package com.example.travel_app

data class Booking(
    val bookingId: String,
    val passengerName: String,
    val passengerPhone: String,
    val airline: String,
    val fromCity: String,
    val toCity: String,
    val departTime: String,
    val arriveTime: String,
    val duration: String,
    val price: Int
)
