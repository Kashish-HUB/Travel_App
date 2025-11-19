package com.example.travel_app

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class TourSummaryActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private lateinit var container: LinearLayout
    private lateinit var txtTotal: TextView
    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour_summary)

        pref = getSharedPreferences("TourBooking", MODE_PRIVATE)
        container = findViewById(R.id.tourSummaryContainer)
        txtTotal = findViewById(R.id.tourSummaryTotal)
        val btnPay = findViewById<Button>(R.id.btnTourPay)

        loadTours()

        btnPay.setOnClickListener {
            if (total == 0) {
                Toast.makeText(this, "No tours selected!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // go to same fake payment page used earlier; sending amount
            val i = Intent(this, Tour::class.java) // reuse fake payment
            i.putExtra("amount", total.toString())
            startActivity(i)
        }
    }

    private fun loadTours() {
        container.removeAllViews()
        total = 0
        val count = pref.getInt("tour_count", 0)
        if (count == 0) {
            txtTotal.text = "Total: ₹0"
            return
        }

        for (i in 1..count) {
            val name = pref.getString("tour_${i}_name", "-") ?: "-"
            val guide = pref.getString("tour_${i}_guide", "-") ?: "-"
            val price = pref.getInt("tour_${i}_price", 0)
            val slot = pref.getString("tour_${i}_slot", "-") ?: "-"
            val date = pref.getString("tour_${i}_date", "-") ?: "-"

            total += price

            // dynamic card (horizontal)
            val card = LinearLayout(this)
            card.orientation = LinearLayout.HORIZONTAL
            card.setPadding(18,18,18,18)
            card.setBackgroundColor(Color.WHITE)
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.setMargins(0,0,0,18)
            card.layoutParams = params

            val info = LinearLayout(this)
            info.orientation = LinearLayout.VERTICAL
            val infoParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            info.layoutParams = infoParams

            val tName = TextView(this)
            tName.text = name
            tName.textSize = 16f
            tName.setTypeface(null, android.graphics.Typeface.BOLD)

            val tGuide = TextView(this)
            tGuide.text = "Guide: $guide"

            val tDate = TextView(this)
            tDate.text = "Date: $date"

            val tSlot = TextView(this)
            tSlot.text = "Slot: $slot"

            val tPrice = TextView(this)
            tPrice.text = "₹$price"
            tPrice.setTextColor(Color.parseColor("#C47A00"))
            tPrice.setTypeface(null, android.graphics.Typeface.BOLD)

            val btnRemove = Button(this)
            btnRemove.text = "Remove"
            btnRemove.setBackgroundColor(Color.parseColor("#FF6868"))
            btnRemove.setTextColor(Color.WHITE)

            val idx = i
            btnRemove.setOnClickListener {
                removeTour(idx)
            }

            info.addView(tName)
            info.addView(tGuide)
            info.addView(tDate)
            info.addView(tSlot)

            card.addView(info)
            card.addView(tPrice)
            card.addView(btnRemove)

            container.addView(card)
        }

        txtTotal.text = "Total: ₹$total"
    }

    private fun removeTour(index: Int) {
        val count = pref.getInt("tour_count", 0)
        for (i in index until count) {
            val next = i + 1
            pref.edit()
                .putString("tour_${i}_name", pref.getString("tour_${next}_name", "-"))
                .putString("tour_${i}_guide", pref.getString("tour_${next}_guide", "-"))
                .putInt("tour_${i}_price", pref.getInt("tour_${next}_price", 0))
                .putString("tour_${i}_slot", pref.getString("tour_${next}_slot", "-"))
                .putString("tour_${i}_date", pref.getString("tour_${next}_date", "-"))
                .apply()
        }

        pref.edit()
            .remove("tour_${count}_name")
            .remove("tour_${count}_guide")
            .remove("tour_${count}_price")
            .remove("tour_${count}_slot")
            .remove("tour_${count}_date")
            .apply()

        pref.edit().putInt("tour_count", count - 1).apply()
        loadTours()
    }
}
