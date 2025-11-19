package com.example.travel_app

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ArtisanSummaryActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private lateinit var container: LinearLayout
    private lateinit var txtTotal: TextView

    private var totalPrice = 0
    private val artisanPrice = 599    // price per artisan workshop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artisan_summary)

        pref = getSharedPreferences("ArtisanBooking", MODE_PRIVATE)
        container = findViewById(R.id.artisanSummaryContainer)
        txtTotal = findViewById(R.id.txtArtisanTotalPrice)

        val btnPay = findViewById<Button>(R.id.btnArtisanPayNow)

        loadArtisans()

        btnPay.setOnClickListener {

            if (totalPrice == 0) {
                Toast.makeText(this, "No artisans selected!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, ArtisanFakePaymentActivity::class.java)
            intent.putExtra("amount", totalPrice.toString())   // sending total amount
            startActivity(intent)
        }
    }

    private fun loadArtisans() {

        container.removeAllViews()
        totalPrice = 0

        val count = pref.getInt("artisan_count", 0)
        if (count == 0) {
            txtTotal.text = "Total: ₹0"
            return
        }

        for (i in 1..count) {

            val name = pref.getString("artisan_${i}_name", "-") ?: "-"
            val skill = pref.getString("artisan_${i}_skill", "-") ?: "-"
            val rating = pref.getString("artisan_${i}_rating", "-") ?: "-"
            val imgRes = pref.getInt("artisan_${i}_img", R.drawable.img_23)

            totalPrice += artisanPrice

            container.addView(createCard(i, name, skill, rating, imgRes))
        }

        txtTotal.text = "Total: ₹$totalPrice"
    }

    private fun createCard(
        index: Int,
        name: String,
        skill: String,
        rating: String,
        imgRes: Int
    ): LinearLayout {

        val card = LinearLayout(this)
        card.orientation = LinearLayout.HORIZONTAL
        card.setPadding(20, 20, 20, 20)
        card.setBackgroundColor(Color.WHITE)
        card.elevation = 8f

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(0, 0, 0, 25)
        card.layoutParams = params

        val img = ImageView(this)
        img.layoutParams = LinearLayout.LayoutParams(180, 180)
        img.setImageResource(imgRes)
        img.scaleType = ImageView.ScaleType.CENTER_CROP

        val infoBox = LinearLayout(this)
        infoBox.orientation = LinearLayout.VERTICAL
        infoBox.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        infoBox.setPadding(20, 0, 0, 0)

        val txtName = TextView(this)
        txtName.text = name
        txtName.textSize = 18f
        txtName.setTypeface(null, android.graphics.Typeface.BOLD)

        val txtSkill = TextView(this)
        txtSkill.text = "Skills: $skill"
        txtSkill.textSize = 15f

        val txtRating = TextView(this)
        txtRating.text = "⭐ Rating: $rating"
        txtRating.textSize = 15f
        txtRating.setTextColor(Color.parseColor("#FFA000"))

        val txtPrice = TextView(this)
        txtPrice.text = "₹$artisanPrice"
        txtPrice.textSize = 17f
        txtPrice.setTextColor(Color.parseColor("#C47A00"))

        infoBox.addView(txtName)
        infoBox.addView(txtSkill)
        infoBox.addView(txtRating)
        infoBox.addView(txtPrice)

        val btnRemove = Button(this)
        btnRemove.text = "Remove"
        btnRemove.textSize = 14f
        btnRemove.setBackgroundColor(Color.parseColor("#FF6868"))
        btnRemove.setTextColor(Color.WHITE)

        btnRemove.setOnClickListener { removeArtisan(index) }

        card.addView(img)
        card.addView(infoBox)
        card.addView(btnRemove)

        return card
    }

    private fun removeArtisan(index: Int) {

        val count = pref.getInt("artisan_count", 0)

        for (i in index until count) {
            val next = i + 1

            pref.edit()
                .putString("artisan_${i}_name", pref.getString("artisan_${next}_name", "-"))
                .putString("artisan_${i}_skill", pref.getString("artisan_${next}_skill", "-"))
                .putString("artisan_${i}_rating", pref.getString("artisan_${next}_rating", "-"))
                .putInt("artisan_${i}_img", pref.getInt("artisan_${next}_img", R.drawable.img_23))
                .apply()
        }

        pref.edit()
            .remove("artisan_${count}_name")
            .remove("artisan_${count}_skill")
            .remove("artisan_${count}_rating")
            .remove("artisan_${count}_img")
            .apply()

        pref.edit().putInt("artisan_count", count - 1).apply()

        loadArtisans()
    }
}
