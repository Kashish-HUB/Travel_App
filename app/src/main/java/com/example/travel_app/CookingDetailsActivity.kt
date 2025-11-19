package com.example.travel_app

import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class CookingDetailsActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences

    private var selectedChefName = ""
    private var selectedChefCuisine = ""
    private var selectedChefRating = ""
    private var selectedSlot = ""
    private var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooking_details)

        pref = getSharedPreferences("BookingData", MODE_PRIVATE)

        val btnChef1 = findViewById<Button>(R.id.btnChef1)
        val btnChef2 = findViewById<Button>(R.id.btnChef2)
        val btnChef3 = findViewById<Button>(R.id.btnChef3)

        val slot9am = findViewById<Button>(R.id.slot9am)
        val slot10am = findViewById<Button>(R.id.slot10am)
        val slot1130am = findViewById<Button>(R.id.slot1130am)
        val slot1pm = findViewById<Button>(R.id.slot1pm)
        val slot230pm = findViewById<Button>(R.id.slot230pm)
        val slot4pm = findViewById<Button>(R.id.slot4pm)
        val slot5pm = findViewById<Button>(R.id.slot5pm)
        val slot7pm = findViewById<Button>(R.id.slot7pm)
        val slot9pm = findViewById<Button>(R.id.slot9pm)
        val btnPickDate = findViewById<Button>(R.id.btnPickDate)
        val btnBookChef = findViewById<Button>(R.id.btnBookChef)

        btnChef1.setOnClickListener {
            selectedChefName = "Chef Aditi"
            selectedChefCuisine = "Indian, Bakery"
            selectedChefRating = "4.9"
            Toast.makeText(this, "$selectedChefName Selected", Toast.LENGTH_SHORT).show()
        }

        btnChef2.setOnClickListener {
            selectedChefName = "Chef Marco"
            selectedChefCuisine = "Italian, Pizza"
            selectedChefRating = "4.7"
            Toast.makeText(this, "$selectedChefName Selected", Toast.LENGTH_SHORT).show()
        }

        btnChef3.setOnClickListener {
            selectedChefName = "Chef Li Wei"
            selectedChefCuisine = "Chinese, Dim Sum"
            selectedChefRating = "4.8"
            Toast.makeText(this, "$selectedChefName Selected", Toast.LENGTH_SHORT).show()
        }

        val slotButtons = listOf(slot9am, slot10am, slot1130am, slot1pm, slot230pm, slot4pm, slot5pm, slot7pm, slot9pm)

        slotButtons.forEach { btn ->
            btn.setOnClickListener {
                selectedSlot = btn.text.toString()
                Toast.makeText(this, "Selected: $selectedSlot", Toast.LENGTH_SHORT).show()
            }
        }

        btnPickDate.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dp = DatePickerDialog(this, { _, y, m, d ->
                selectedDate = "$d-${m + 1}-$y"
                Toast.makeText(this, "Date Selected: $selectedDate", Toast.LENGTH_SHORT).show()

            }, year, month, day)

            dp.show()
        }

        btnBookChef.setOnClickListener {

            if (selectedChefName.isEmpty() || selectedSlot.isEmpty() || selectedDate.isEmpty()) {
                Toast.makeText(this, "Select chef, slot & date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            saveChefToList()

            startActivity(Intent(this, BookingSummaryActivity::class.java))
        }
    }

    private fun saveChefToList() {

        val count = pref.getInt("chef_count", 0) + 1

        pref.edit()
            .putString("chef_${count}_name", selectedChefName)
            .putString("chef_${count}_cuisine", selectedChefCuisine)
            .putString("chef_${count}_rating", selectedChefRating)
            .putString("chef_${count}_slot", selectedSlot)
            .putString("chef_${count}_date", selectedDate)
            .putInt("chef_count", count)
            .apply()

        Toast.makeText(this, "Added to Booking List", Toast.LENGTH_SHORT).show()
    }
}
