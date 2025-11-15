package com.example.travel_app

import android.content.Context

object HotelWishlist {
    private const val PREF_NAME = "hotel_wishlist"
    private const val KEY_SET = "wishlist_names"

    fun add(context: Context, hotelName: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val set = prefs.getStringSet(KEY_SET, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        set.add(hotelName)
        prefs.edit().putStringSet(KEY_SET, set).apply()
    }

    fun remove(context: Context, hotelName: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val set = prefs.getStringSet(KEY_SET, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        set.remove(hotelName)
        prefs.edit().putStringSet(KEY_SET, set).apply()
    }

    fun contains(context: Context, hotelName: String): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val set = prefs.getStringSet(KEY_SET, mutableSetOf()) ?: mutableSetOf()
        return set.contains(hotelName)
    }

    fun all(context: Context): Set<String> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getStringSet(KEY_SET, emptySet()) ?: emptySet()
    }
}
