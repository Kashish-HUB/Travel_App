package com.example.travel_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BookingActivity : AppCompatActivity() {

    private lateinit var flight: Flight

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        flight = intent.getSerializableExtra("flight") as Flight

        val tvFlightSummary = findViewById<TextView>(R.id.tvFlightSummary)
        val tvPriceSummary = findViewById<TextView>(R.id.tvPriceSummary)
        val etPassengerName = findViewById<EditText>(R.id.etPassengerName)
        val etPassengerPhone = findViewById<EditText>(R.id.etPassengerPhone)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)

        tvFlightSummary.text = "${flight.airline} — ${flight.fromCity} → ${flight.toCity}"
        tvPriceSummary.text = "Price: ₹ ${flight.price}"

        btnConfirm.setOnClickListener {

            val name = etPassengerName.text.toString().trim()
            val phone = etPassengerPhone.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please enter passenger name and phone", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val bookingId = "BK" + System.currentTimeMillis().toString().takeLast(5)

            val booking = Booking(
                bookingId,
                name,
                phone,
                flight.airline,
                flight.fromCity,
                flight.toCity,
                flight.departTime,
                flight.arriveTime,
                flight.duration,
                flight.price
            )

            BookingStorage.saveBooking(this, booking)

            Toast.makeText(this, "Booking Saved! ID: $bookingId", Toast.LENGTH_LONG).show()

            finish()
        }
    }
}
