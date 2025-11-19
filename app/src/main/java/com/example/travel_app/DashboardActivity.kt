package com.example.travel_app

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.RatingBar
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class DashboardActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var firebaseAuth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        drawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, R.string.open, R.string.close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadUserData()

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_flight -> startActivity(Intent(this, BookFlightActivity::class.java))

                R.id.nav_hotels -> startActivity(Intent(this, FindHotelsActivity::class.java))

                R.id.nav_explore -> startActivity(Intent(this, ExploreStatesActivity::class.java))

                R.id.nav_culture -> startActivity(Intent(this, CultureActivity::class.java))

                R.id.nav_safety -> startActivity(Intent(this, TravelSafetyActivity::class.java))

                R.id.nav_deals -> startActivity(Intent(this, OfferActivity::class.java))

                R.id.nav_profile -> {
                    Toast.makeText(this, "App Version: 1.0\nDeveloped by: Your Name", Toast.LENGTH_LONG).show()
                }
                R.id.nav_settings -> {
                    Toast.makeText(this, "Contact us at support@travelapp.com", Toast.LENGTH_LONG).show()
                }
                R.id.nav_logout -> {
                    firebaseAuth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }

            drawerLayout.closeDrawers()
            true
        }

        findViewById<androidx.cardview.widget.CardView>(R.id.cardTravel).setOnClickListener {
            startActivity(Intent(this, BookFlightActivity::class.java))
        }

        findViewById<androidx.cardview.widget.CardView>(R.id.cardHotel).setOnClickListener {
            startActivity(Intent(this, FindHotelsActivity::class.java))
        }

        findViewById<androidx.cardview.widget.CardView>(R.id.cardExplore).setOnClickListener {
            startActivity(Intent(this, ExploreStatesActivity::class.java))
        }

        findViewById<androidx.cardview.widget.CardView>(R.id.cardCulture).setOnClickListener {
            startActivity(Intent(this, CultureActivity::class.java))
        }

        findViewById<androidx.cardview.widget.CardView>(R.id.cardSafety).setOnClickListener {
            startActivity(Intent(this, TravelSafetyActivity::class.java))
        }

        findViewById<androidx.cardview.widget.CardView>(R.id.cardDeals).setOnClickListener {
            startActivity(Intent(this, OfferActivity::class.java))
        }

        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val etFeedback = findViewById<TextView>(R.id.etFeedback)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitFeedback)

        loadFeedback()

        btnSubmit.setOnClickListener {
            saveFeedback(ratingBar.rating, etFeedback.text.toString())
        }

    }

    private fun loadUserData() {
        val user = firebaseAuth.currentUser ?: return
        val userId = user.uid

        val header = navigationView.getHeaderView(0)
        val userName = header.findViewById<TextView>(R.id.headerUserName)
        val userEmail = header.findViewById<TextView>(R.id.headerUserEmail)
        val userImage = header.findViewById<ImageView>(R.id.headerProfileImage)

        userEmail.text = user.email ?: "No email"

        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) {
                    val name = doc.getString("name")
                    val profileUrl = doc.getString("profileUrl")

                    userName.text = name ?: "User"

                    if (!profileUrl.isNullOrEmpty()) {
                        loadImageFromFirebase(profileUrl, userImage)
                    }
                }
            }
            .addOnFailureListener {
                userName.text = "User"
            }
    }

    private fun loadImageFromFirebase(url: String, imageView: ImageView) {
        val storageRef = storage.getReferenceFromUrl(url)

        storageRef.getBytes(2 * 1024 * 1024)
            .addOnSuccessListener { bytes ->
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                imageView.setImageBitmap(bitmap)
            }
            .addOnFailureListener {
            }
    }
    private fun saveFeedback(rating: Float, feedback: String) {
        val sharedPref = getSharedPreferences("DashboardFeedback", MODE_PRIVATE)
        val editor = sharedPref.edit()

        editor.putFloat("rating", rating)
        editor.putString("feedback", feedback)

        editor.apply()

        Toast.makeText(this, "Feedback Submitted!", Toast.LENGTH_SHORT).show()
    }
    private fun loadFeedback() {
        val sharedPref = getSharedPreferences("DashboardFeedback", MODE_PRIVATE)
        val rating = sharedPref.getFloat("rating", 0f)
        val feedback = sharedPref.getString("feedback", "")

        findViewById<RatingBar>(R.id.ratingBar).rating = rating
        findViewById<TextView>(R.id.etFeedback).text = feedback
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle.onOptionsItemSelected(item)) true
        else super.onOptionsItemSelected(item)
    }
}
