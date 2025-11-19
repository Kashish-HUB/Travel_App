package com.example.travel_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExploreStatesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore_states)

        val rvStates = findViewById<RecyclerView>(R.id.rvStates)

        val states = listOf(
            State(
                "Rajasthan",
                "Land of forts, deserts, palaces, and vibrant culture.",
                listOf("Jaipur", "Udaipur", "Jodhpur"),
                listOf("Dal Baati", "Ghewar"),
                listOf(R.drawable.rajasthan1),
                "Rajasthan"
            ),

            State(
                "Kerala",
                "Known for backwaters, beaches, and lush greenery.",
                listOf("Munnar", "Alleppey", "Kochi"),
                listOf("Puttu", "Appam"),
                listOf(R.drawable.kerala1),
                "Kerala"
            ),

            State(
                "Goa",
                "Famous for beaches, nightlife, and water sports.",
                listOf("Calangute Beach", "Baga Beach", "Panaji"),
                listOf("Fish Curry", "Prawn Balchao"),
                listOf(R.drawable.goa1),
                "Goa"
            ),

            State(
                "Delhi",
                "Capital city with heritage, food, and culture.",
                listOf("India Gate", "Red Fort", "Qutub Minar"),
                listOf("Chole Bhature", "Parathas"),
                listOf(R.drawable.delhi1),
                "New Delhi"
            ),

            State(
                "Maharashtra",
                "Home of Mumbai, beaches, caves, and hill stations.",
                listOf("Mumbai", "Pune", "Lonavala"),
                listOf("Vada Pav", "Misal Pav"),
                listOf(R.drawable.maharashtra1),
                "Maharashtra"
            ),

            State(
                "Uttarakhand",
                "Land of mountains, lakes, and spiritual centers.",
                listOf("Nainital", "Mussoorie", "Rishikesh"),
                listOf("Kafuli", "Aloo ke Gutke"),
                listOf(R.drawable.uttarakhand1),
                "Uttarakhand"
            ),

            State(
                "Himachal Pradesh",
                "Popular for snowy mountains and adventure sports.",
                listOf("Manali", "Shimla", "Dharamshala"),
                listOf("Dham", "Siddu"),
                listOf(R.drawable.himachal1),
                "Himachal Pradesh"
            ),

            State(
                "West Bengal",
                "Known for culture, sweets, mountains, and beaches.",
                listOf("Kolkata", "Darjeeling", "Sundarbans"),
                listOf("Rosogolla", "Fish Curry"),
                listOf(R.drawable.westbengal1),
                "West Bengal"
            ),

            State(
                "Tamil Nadu",
                "Land of temples, beaches, and classical culture.",
                listOf("Chennai", "Ooty", "Kanyakumari"),
                listOf("Idli", "Dosa"),
                listOf(R.drawable.tamilnadu1),
                "Tamil Nadu"
            ),

            State(
                "Karnataka",
                "Famous for IT hub, heritage temples, coffee estates.",
                listOf("Bangalore", "Mysore", "Coorg"),
                listOf("Bisi Bele Bath", "Mysore Pak"),
                listOf(R.drawable.karnataka1),
                "Karnataka"
            ),

            State(
                "Punjab",
                "Known for fields, culture, and delicious food.",
                listOf("Amritsar", "Ludhiana", "Chandigarh"),
                listOf("Sarson da Saag", "Butter Chicken"),
                listOf(R.drawable.punjab1),
                "Punjab"
            ),

            State(
                "Gujarat",
                "Land of white desert, culture, and beautiful coastlines.",
                listOf("Kutch", "Ahmedabad", "Somnath"),
                listOf("Dhokla", "Thepla"),
                listOf(R.drawable.gujarat1),
                "Gujarat"
            )
        )


        rvStates.layoutManager = GridLayoutManager(this, 2)
        rvStates.adapter = StateAdapter(states) { state ->
            val intent = Intent(this, StateDetailsActivity::class.java)
            intent.putExtra("state", state)
            startActivity(intent)
        }
    }
}
