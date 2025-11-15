package com.example.travel_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HotelDetailsActivity : AppCompatActivity() {

    private lateinit var hotel: Hotel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel_details)

        hotel = intent.getSerializableExtra("hotel") as Hotel

        findViewById<ImageView>(R.id.imgHotelDetail).setImageResource(hotel.imageRes)
        findViewById<TextView>(R.id.tvHotelNameDetail).text = hotel.name
        findViewById<TextView>(R.id.tvRatingDetail).text = "⭐ ${hotel.rating}"
        findViewById<TextView>(R.id.tvAmenities).text = "Amenities: ${hotel.amenities.joinToString(", ")}"
        findViewById<TextView>(R.id.tvDescription).text = hotel.description
        findViewById<TextView>(R.id.tvPriceDetail).text = "₹ ${hotel.price} / night"

        findViewById<Button>(R.id.btnBookNow).setOnClickListener {
            val i = Intent(this, HotelBookingActivity::class.java)
            i.putExtra("hotel", hotel)
            startActivity(i)
        }
    }
}
