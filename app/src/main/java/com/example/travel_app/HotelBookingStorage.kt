package com.example.travel_app

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object HotelBookingStorage {

    private const val PREF_NAME = "hotel_bookings_pref"
    private const val KEY_BOOKINGS = "hotel_bookings"

    fun saveBooking(context: Context, booking: HotelBooking) {
        val sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()

        val listType = object : TypeToken<MutableList<HotelBooking>>() {}.type

        val currentList: MutableList<HotelBooking> =
            gson.fromJson(sharedPrefs.getString(KEY_BOOKINGS, "[]"), listType)

        currentList.add(booking)

        sharedPrefs.edit().putString(KEY_BOOKINGS, gson.toJson(currentList)).apply()
    }

    fun getBookings(context: Context): MutableList<HotelBooking> {
        val sharedPrefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val listType = object : TypeToken<MutableList<HotelBooking>>() {}.type

        return gson.fromJson(sharedPrefs.getString(KEY_BOOKINGS, "[]"), listType)
    }
}
