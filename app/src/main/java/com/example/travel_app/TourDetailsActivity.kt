package com.example.travel_app

import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TourDetailsActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private lateinit var spinnerTours: Spinner
    private lateinit var txtGuideInfo: TextView
    private lateinit var txtPriceInfo: TextView
    private lateinit var pickDateBtn: Button
    private lateinit var slot930: Button
    private lateinit var slot1pm: Button
    private lateinit var slot4pm: Button
    private lateinit var slot730: Button
    private lateinit var addToCartBtn: Button
    private lateinit var viewSummaryBtn: Button

    private var selectedSlot: String? = null
    private var selectedDate: String? = null

    private val tours = listOf(
        "Heritage City Walk",
        "Traditional Village Visit",
        "Food Culture Street Tour",
        "Temple & Spiritual Tour",
        "Folk Dance & Music Show"
    )

    private val guides = listOf(
        "Priya — Heritage Walk Expert",
        "Mehul — Village & Rural Experience",
        "Kabir — Street Food Guide",
        "Radha — Temple & Mythology Specialist",
        "Anaya — Folk Dance & Music Presenter"
    )

    private val prices = listOf(599, 499, 699, 549, 799)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour_details)

        pref = getSharedPreferences("TourBooking", MODE_PRIVATE)

        spinnerTours = findViewById(R.id.spinnerTours)
        txtGuideInfo = findViewById(R.id.txtGuideInfo)
        txtPriceInfo = findViewById(R.id.txtPriceInfo)
        pickDateBtn = findViewById(R.id.btnPickTourDate)
        slot930 = findViewById(R.id.slot930)
        slot1pm = findViewById(R.id.slot1pm)
        slot4pm = findViewById(R.id.slot4pm)
        slot730 = findViewById(R.id.slot730)
        addToCartBtn = findViewById(R.id.btnAddToCart)
        viewSummaryBtn = findViewById(R.id.btnViewSummary)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tours)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTours.adapter = adapter

        spinnerTours.setSelection(0)
        updateTourInfo(0)

        spinnerTours.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                updateTourInfo(position)
                selectedSlot = null
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        pickDateBtn.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                selectedDate = "$d-${m + 1}-$y"
                Toast.makeText(this, "Date: $selectedDate", Toast.LENGTH_SHORT).show()
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }

        val slotClick: (String, Button) -> Unit = { s, b ->
            selectedSlot = s
            // reset backgrounds
            listOf(slot930, slot1pm, slot4pm, slot730).forEach { it.setBackgroundColor(resources.getColor(android.R.color.darker_gray, null)) }
            b.setBackgroundColor(resources.getColor(android.R.color.holo_orange_light, null))
            Toast.makeText(this, "Slot: $s", Toast.LENGTH_SHORT).show()
        }

        slot930.setOnClickListener { slotClick("9:30 AM", slot930) }
        slot1pm.setOnClickListener { slotClick("1:00 PM", slot1pm) }
        slot4pm.setOnClickListener { slotClick("4:00 PM", slot4pm) }
        slot730.setOnClickListener { slotClick("7:30 PM", slot730) }

        addToCartBtn.setOnClickListener {
            val pos = spinnerTours.selectedItemPosition
            val tourName = tours[pos]
            val guide = guides[pos]
            val price = prices[pos]

            if (selectedSlot == null || selectedDate == null) {
                Toast.makeText(this, "Please choose date and time slot", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val count = pref.getInt("tour_count", 0) + 1
            pref.edit()
                .putInt("tour_count", count)
                .putString("tour_${count}_name", tourName)
                .putString("tour_${count}_guide", guide)
                .putInt("tour_${count}_price", price)
                .putString("tour_${count}_slot", selectedSlot)
                .putString("tour_${count}_date", selectedDate)
                .apply()

            Toast.makeText(this, "$tourName added to cart", Toast.LENGTH_SHORT).show()
        }

        viewSummaryBtn.setOnClickListener {
            startActivity(Intent(this, TourSummaryActivity::class.java))
        }
    }

    private fun updateTourInfo(position: Int) {
        txtGuideInfo.text = "Guide: ${guides[position]}"
        txtPriceInfo.text = "Price: ₹${prices[position]}"
    }
}
