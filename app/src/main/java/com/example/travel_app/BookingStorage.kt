package com.example.travel_app

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object BookingStorage {

    private const val PREF_NAME = "BookingsData"
    private const val KEY_BOOKINGS = "bookings"

    fun saveBooking(context: Context, booking: Booking) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val gson = Gson()

        // Load old bookings
        val json = prefs.getString(KEY_BOOKINGS, null)
        val type = object : TypeToken<MutableList<Booking>>() {}.type
        val list: MutableList<Booking> =
            if (json != null) gson.fromJson(json, type)
            else mutableListOf()

        list.add(booking)

        prefs.edit().putString(KEY_BOOKINGS, gson.toJson(list)).apply()
    }

    fun getBookings(context: Context): List<Booking> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_BOOKINGS, null) ?: return emptyList()
        val type = object : TypeToken<List<Booking>>() {}.type
        return Gson().fromJson(json, type)
    }
}
