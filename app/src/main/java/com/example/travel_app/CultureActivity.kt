package com.example.travel_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class CultureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_culture)
        val cardCooking = findViewById<CardView>(R.id.cardCooking)
        val cardArtisan = findViewById<CardView>(R.id.cardArtisan)
        val cardTour = findViewById<CardView>(R.id.cardTour)

        val btnJoinCooking = findViewById<Button>(R.id.btnJoinCooking)
        val btnJoinArtisan = findViewById<Button>(R.id.btnJoinArtisan)
        val btnJoinTour = findViewById<Button>(R.id.btnJoinTour)


        cardCooking.setOnClickListener {
            val intent = Intent(this, CookingDetailsActivity::class.java)
            startActivity(intent)
        }

        cardArtisan.setOnClickListener {
            val intent = Intent(this, ArtisanDetailsActivity::class.java)
            startActivity(intent)
        }

        cardTour.setOnClickListener {
            val intent = Intent(this, TourDetailsActivity::class.java)
            startActivity(intent)
        }

        btnJoinCooking.setOnClickListener {
            val intent = Intent(this, CookingDetailsActivity::class.java)
            startActivity(intent)
        }

        btnJoinArtisan.setOnClickListener {
            val intent = Intent(this, ArtisanDetailsActivity::class.java)
            startActivity(intent)
        }

        btnJoinTour.setOnClickListener {
            val intent = Intent(this, TourDetailsActivity::class.java)
            startActivity(intent)
        }
    }
}
