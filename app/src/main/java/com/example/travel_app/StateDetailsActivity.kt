package com.example.travel_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StateDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_details)

        val state = intent.getParcelableExtra<State>("state")!!

        findViewById<TextView>(R.id.tvStateName).text = state.name
        findViewById<TextView>(R.id.tvDescription).text = state.description
        findViewById<TextView>(R.id.tvPopularPlaces).text = state.popularPlaces.joinToString()
        findViewById<TextView>(R.id.tvDishes).text = state.famousDishes.joinToString()

        val rv = findViewById<RecyclerView>(R.id.rvImages)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = ImageAdapter(state.images)

        findViewById<Button>(R.id.btnMap).setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${state.mapQuery}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }
}
