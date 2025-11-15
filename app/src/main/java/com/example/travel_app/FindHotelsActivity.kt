package com.example.travel_app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast

class FindHotelsActivity : AppCompatActivity() {

    private lateinit var adapter: HotelAdapter
    private lateinit var allHotels: List<Hotel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_hotels)

        // create dummy hotels
        allHotels = getDummyHotels()

        val rv = findViewById<RecyclerView>(R.id.rvHotels)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = HotelAdapter(
            allHotels,
            onViewDetails = { hotel -> openDetails(hotel) },
            onToggleWishlist = { hotel -> toggleWishlist(hotel) },
            isInWishlist = { hotel -> HotelWishlist.contains(this, hotel.name) }
        )
        rv.adapter = adapter

        // search
        val et = findViewById<EditText>(R.id.etSearchCity)
        et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val q = s?.toString()?.trim()?.lowercase() ?: ""
                val filtered = if (q.isEmpty()) allHotels else allHotels.filter {
                    it.city.lowercase().contains(q) || it.name.lowercase().contains(q)
                }
                adapter.updateList(filtered)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // filters buttons
        findViewById<Button>(R.id.filterRating).setOnClickListener {
            val sorted = allHotels.sortedByDescending { it.rating }
            adapter.updateList(sorted)
            Toast.makeText(this, "Sorted by top rating", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.filterPriceLow).setOnClickListener {
            val sorted = allHotels.sortedBy { it.price }
            adapter.updateList(sorted)
            Toast.makeText(this, "Sorted by low price", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.filterLuxury).setOnClickListener {
            val filtered = allHotels.filter { it.rating >= 4.5 }
            adapter.updateList(filtered)
            Toast.makeText(this, "Showing luxury 5★+", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openDetails(hotel: Hotel) {
        val i = Intent(this, HotelDetailsActivity::class.java)
        i.putExtra("hotel", hotel)
        startActivity(i)
    }

    private fun toggleWishlist(hotel: Hotel) {
        if (HotelWishlist.contains(this, hotel.name)) {
            HotelWishlist.remove(this, hotel.name)
            Toast.makeText(this, "${hotel.name} removed from wishlist", Toast.LENGTH_SHORT).show()
        } else {
            HotelWishlist.add(this, hotel.name)
            Toast.makeText(this, "${hotel.name} saved to wishlist", Toast.LENGTH_SHORT).show()
        }
    }

    // Dummy data - replace image resource ids with your drawables
    private fun getDummyHotels(): List<Hotel> {
        return listOf(
            Hotel("Taj Palace", "Delhi", 4.9, 8500, R.drawable.sample_hotel_1,
                listOf("Free WiFi", "Pool", "Spa", "Parking"),
                "Luxury 5-star hotel located in the heart of Delhi."
            ),
            Hotel("The Leela Ambience", "Delhi", 4.8, 7200, R.drawable.sample_hotel_2,
                listOf("Spa", "Pool", "Bar"), "Premium hotel near airport."
            ),
            Hotel("ITC Maurya", "Delhi", 4.7, 6800, R.drawable.sample_hotel_3,
                listOf("Gym", "Spa"), "Top luxury hotel with iconic restaurants."
            ),

            Hotel("The Oberoi", "Mumbai", 4.8, 7800, R.drawable.sample_hotel_4,
                listOf("Sea View", "Gym", "Bar", "Airport Shuttle"),
                "Premium sea-facing hotel with world-class service."
            ),
            Hotel("Taj Lands End", "Mumbai", 4.6, 7100, R.drawable.sample_hotel_5,
                listOf("Pool", "Gym"), "Located in Bandra with seafront view."
            ),
            Hotel("JW Marriott Juhu", "Mumbai", 4.5, 6900, R.drawable.sample_hotel_6,
                listOf("Beachfront", "Spa"), "Luxury stay in Juhu."

            ),
            Hotel("Novotel", "Kolkata", 4.4, 4200, R.drawable.sample_hotel_1,
                listOf("Spa", "Gym", "Pool"), "4-star business hotel near airport."
            ),
            Hotel("The Oberoi Grand", "Kolkata", 4.7, 6400, R.drawable.sample_hotel_2,
                listOf("Restaurant", "Pool"), "Iconic heritage hotel."
            ),

            Hotel("The Leela Palace", "Bengaluru", 4.7, 6500, R.drawable.sample_hotel_3,
                listOf("Free Breakfast", "Pool", "Parking"),
                "Elegant hotel with royal architecture."
            ),
            Hotel("Taj MG Road", "Bengaluru", 4.6, 5800, R.drawable.sample_hotel_4,
                listOf("Gym", "Spa"), "Modern hotel with great connectivity."
            ),

            Hotel("ITC Kohenur", "Hyderabad", 4.6, 6000, R.drawable.sample_hotel_5,
                listOf("Lake View", "Spa"), "Luxury hotel in HITEC city."
            ),
            Hotel("Taj Krishna", "Hyderabad", 4.5, 5400, R.drawable.sample_hotel_6,
                listOf("Pool", "Bar"), "Premium upscale hotel."
            ),

            Hotel("Paradise Resort", "Goa", 4.5, 5200, R.drawable.sample_hotel_1,
                listOf("Beach View", "Pool", "Restaurant"),
                "Relaxing beachfront resort."
            ),
            Hotel("Grand Hyatt", "Goa", 4.7, 7800, R.drawable.sample_hotel_2,
                listOf("Spa", "Pool"), "Luxury tropical resort."
            ),
            Hotel("Alila Diwa", "Goa", 4.6, 6900, R.drawable.sample_hotel_3,
                listOf("Spa", "Bar"), "Premium wellness resort."
            ),

            Hotel("Radisson Blu", "Chennai", 4.3, 4100, R.drawable.sample_hotel_4,
                listOf("Pool", "Gym"), "Business-friendly hotel."
            ),
            Hotel("Fortune Park", "Chennai", 4.2, 3800, R.drawable.sample_hotel_5,
                listOf("Free WiFi", "Gym"), "Comfortable business hotel."
            ),

            Hotel("Fairfield Marriott", "Pune", 4.4, 3500, R.drawable.sample_hotel_6,
                listOf("Bar", "Gym"), "Great value hotel."
            ),
            Hotel("Conrad", "Pune", 4.7, 7300, R.drawable.sample_hotel_1,
                listOf("Pool", "Spa"), "Premium 5-star business hotel."
            ),

            Hotel("Rambagh Palace", "Jaipur", 4.9, 12000, R.drawable.sample_hotel_2,
                listOf("Luxury Suites", "Garden"), "Royal heritage palace hotel."
            ),
            Hotel("Trident", "Jaipur", 4.6, 5600, R.drawable.sample_hotel_3,
                listOf("Pool", "Restaurant"), "Comfort stay near Amber fort."
            ),

            Hotel("The Lalit", "Chandigarh", 4.5, 5400, R.drawable.sample_hotel_4,
                listOf("Pool", "Free WiFi"), "Premium luxury hotel."
            ),
            Hotel("Hyatt Regency", "Lucknow", 4.4, 4800, R.drawable.sample_hotel_5,
                listOf("Spa", "Gym"), "Modern stylish hotel."
            ),
            Hotel("Grand Hyatt", "Kochi", 4.7, 7500, R.drawable.sample_hotel_6,
                listOf("Lake View", "Spa"), "Beautiful waterfront hotel."
            )
        )
    }

}
