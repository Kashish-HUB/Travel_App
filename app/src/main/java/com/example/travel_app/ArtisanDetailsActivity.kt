package com.example.travel_app

import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ArtisanDetailsActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artisan_details)

        pref = getSharedPreferences("ArtisanBooking", MODE_PRIVATE)

        val btnAdd1 = findViewById<Button>(R.id.btnAddArtisan1)
        val btnAdd2 = findViewById<Button>(R.id.btnAddArtisan2)
        val btnAdd3 = findViewById<Button>(R.id.btnAddArtisan3)

        val slotMorning = findViewById<Button>(R.id.artSlotMorning)
        val slotAfternoon = findViewById<Button>(R.id.artSlotAfternoon)
        val slotEvening = findViewById<Button>(R.id.artSlotEvening)

        val btnPickDate = findViewById<Button>(R.id.btnPickArtisanDate)

        val btnBookWorkshop = findViewById<Button>(R.id.btnBookArtisan)

        btnAdd1.setOnClickListener {
            addArtisan(
                name = "Arti – Pottery Expert",
                skill = "Pottery, Clay Art",
                rating = "4.8",
                img = R.drawable.img_23
            )
        }

        btnAdd2.setOnClickListener {
            addArtisan(
                name = "Raman – Wood Carver",
                skill = "Wood Carving, Sculpture",
                rating = "4.9",
                img = R.drawable.img_24
            )
        }

        btnAdd3.setOnClickListener {
            addArtisan(
                name = "Meera – Hand Weaving Master",
                skill = "Weaving, Fabric Design",
                rating = "4.7",
                img = R.drawable.img_25
            )
        }

        slotMorning.setOnClickListener { saveSlot("10:00 AM") }
        slotAfternoon.setOnClickListener { saveSlot("1:00 PM") }
        slotEvening.setOnClickListener { saveSlot("5:00 PM") }

        btnPickDate.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                val date = "$d-${m + 1}-$y"
                pref.edit().putString("artisan_date", date).apply()
                Toast.makeText(this, "Selected Date: $date", Toast.LENGTH_SHORT).show()
            }, year, month, day).show()
        }

        btnBookWorkshop.setOnClickListener {

            val count = pref.getInt("artisan_count", 0)
            val slot = pref.getString("artisan_slot", null)
            val date = pref.getString("artisan_date", null)

            if (count == 0) {
                Toast.makeText(this, "Select at least 1 Artisan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (slot == null || date == null) {
                Toast.makeText(this, "Select Time Slot & Date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            startActivity(Intent(this, ArtisanSummaryActivity::class.java))
        }
    }

    private fun addArtisan(name: String, skill: String, rating: String, img: Int) {

        val count = pref.getInt("artisan_count", 0) + 1

        pref.edit()
            .putString("artisan_${count}_name", name)
            .putString("artisan_${count}_skill", skill)
            .putString("artisan_${count}_rating", rating)
            .putInt("artisan_${count}_img", img)
            .putInt("artisan_count", count)
            .apply()

        Toast.makeText(this, "$name Added", Toast.LENGTH_SHORT).show()
    }

    private fun saveSlot(slot: String) {
        pref.edit().putString("artisan_slot", slot).apply()
        Toast.makeText(this, "Slot Selected: $slot", Toast.LENGTH_SHORT).show()
    }
}
