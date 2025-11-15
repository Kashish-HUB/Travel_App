package com.example.travel_app

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class HotelBookingActivity : AppCompatActivity() {

    private lateinit var hotel: Hotel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_booking)

        hotel = intent.getSerializableExtra("hotel") as Hotel

        val tvHotelName = findViewById<TextView>(R.id.tvHotelNameBooking)
        val etCheckIn = findViewById<EditText>(R.id.etCheckIn)
        val etCheckOut = findViewById<EditText>(R.id.etCheckOut)
        val etGuests = findViewById<EditText>(R.id.etGuests)
        val btnConfirm = findViewById<Button>(R.id.btnConfirmBooking)

        tvHotelName.text = hotel.name

        // -----------------------------------------------------
        // SHOW CALENDAR FOR CHECK-IN
        // -----------------------------------------------------
        etCheckIn.setOnClickListener {
            showCalendar { date -> etCheckIn.setText(date) }
        }

        // -----------------------------------------------------
        // SHOW CALENDAR FOR CHECK-OUT
        // -----------------------------------------------------
        etCheckOut.setOnClickListener {
            showCalendar { date -> etCheckOut.setText(date) }
        }

        btnConfirm.setOnClickListener {

            val checkIn = etCheckIn.text.toString()
            val checkOut = etCheckOut.text.toString()
            val guests = etGuests.text.toString().toIntOrNull() ?: 1

            if (checkIn.isEmpty() || checkOut.isEmpty()) {
                Toast.makeText(this, "Enter valid dates", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val booking = HotelBooking(
                hotelName = hotel.name,
                price = hotel.price,
                checkIn = checkIn,
                checkOut = checkOut,
                guests = guests
            )

            HotelBookingStorage.saveBooking(this, booking)

            Toast.makeText(this, "Hotel Booked Successfully!", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    // -----------------------------------------------------
    // FUNCTION TO SHOW DATE PICKER
    // -----------------------------------------------------
    private fun showCalendar(onSelected: (String) -> Unit) {
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val picker = DatePickerDialog(this, { _, y, m, d ->
            onSelected("$d/${m + 1}/$y")
        }, year, month, day)

        picker.datePicker.minDate = System.currentTimeMillis() // disable past dates
        picker.show()
    }
}
