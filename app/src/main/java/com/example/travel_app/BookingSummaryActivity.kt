package com.example.travel_app

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class BookingSummaryActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences
    private lateinit var chefContainer: LinearLayout
    private lateinit var txtTotal: TextView

    private var totalPrice = 0
    private val chefPrice = 799   // price per chef

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_summary)

        pref = getSharedPreferences("BookingData", MODE_PRIVATE)
        chefContainer = findViewById(R.id.summaryChefContainer)
        txtTotal = findViewById(R.id.summaryTotalPrice)

        val btnPayNow = findViewById<Button>(R.id.btnPayNow)

        loadChefs()

        btnPayNow.setOnClickListener {
            if (totalPrice == 0) {
                Toast.makeText(this, "No chefs selected!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            openFakePayment(totalPrice.toString())
        }
    }

    private fun loadChefs() {

        chefContainer.removeAllViews()
        totalPrice = 0

        val count = pref.getInt("chef_count", 0)

        if (count == 0) {
            txtTotal.text = "Total: ₹0"
            return
        }

        for (i in 1..count) {

            val name = pref.getString("chef_${i}_name", "-") ?: "-"
            val cuisine = pref.getString("chef_${i}_cuisine", "-") ?: "-"
            val rating = pref.getString("chef_${i}_rating", "-") ?: "-"
            val date = pref.getString("chef_${i}_date", "-") ?: "-"
            val slot = pref.getString("chef_${i}_slot", "-") ?: "-"

            totalPrice += chefPrice

            chefContainer.addView(createChefCard(i, name, cuisine, rating, date, slot))
        }

        txtTotal.text = "Total: ₹$totalPrice"
    }

    private fun createChefCard(
        index: Int,
        name: String,
        cuisine: String,
        rating: String,
        date: String,
        slot: String
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
        img.scaleType = ImageView.ScaleType.CENTER_CROP
        img.setImageResource(
            when (name) {
                "Chef Aditi" -> R.drawable.img_19
                "Chef Marco" -> R.drawable.img_20
                "Chef Li Wei" -> R.drawable.img_21
                else -> R.drawable.img_15
            }
        )

        val col = LinearLayout(this)
        col.orientation = LinearLayout.VERTICAL
        col.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        col.setPadding(20, 0, 0, 0)

        val txtName = TextView(this)
        txtName.text = name
        txtName.textSize = 18f
        txtName.setTextColor(Color.BLACK)
        txtName.setTypeface(null, android.graphics.Typeface.BOLD)

        val txtCuisine = TextView(this)
        txtCuisine.text = "Cuisine: $cuisine"
        txtCuisine.textSize = 15f

        val txtRating = TextView(this)
        txtRating.text = "⭐ Rating: $rating"
        txtRating.setTextColor(Color.parseColor("#FFA000"))
        txtRating.textSize = 15f

        val txtDate = TextView(this)
        txtDate.text = "Date: $date"
        txtDate.textSize = 15f

        val txtSlot = TextView(this)
        txtSlot.text = "Time: $slot"
        txtSlot.textSize = 15f

        val txtPrice = TextView(this)
        txtPrice.text = "₹$chefPrice"
        txtPrice.textSize = 17f
        txtPrice.setTextColor(Color.parseColor("#C47A00"))
        txtPrice.setTypeface(null, android.graphics.Typeface.BOLD)

        col.addView(txtName)
        col.addView(txtCuisine)
        col.addView(txtRating)
        col.addView(txtDate)
        col.addView(txtSlot)
        col.addView(txtPrice)

        val btnRemove = Button(this)
        btnRemove.text = "Remove"
        btnRemove.textSize = 14f
        btnRemove.setBackgroundColor(Color.parseColor("#FF6868"))
        btnRemove.setTextColor(Color.WHITE)

        btnRemove.setOnClickListener {
            removeChef(index)
        }

        card.addView(img)
        card.addView(col)
        card.addView(btnRemove)

        return card
    }

    private fun removeChef(index: Int) {

        val count = pref.getInt("chef_count", 0)

        for (i in index until count) {
            val next = i + 1

            pref.edit()
                .putString("chef_${i}_name", pref.getString("chef_${next}_name", "-"))
                .putString("chef_${i}_cuisine", pref.getString("chef_${next}_cuisine", "-"))
                .putString("chef_${i}_rating", pref.getString("chef_${next}_rating", "-"))
                .putString("chef_${i}_date", pref.getString("chef_${next}_date", "-"))
                .putString("chef_${i}_slot", pref.getString("chef_${next}_slot", "-"))
                .apply()
        }

        pref.edit()
            .remove("chef_${count}_name")
            .remove("chef_${count}_cuisine")
            .remove("chef_${count}_rating")
            .remove("chef_${count}_slot")
            .remove("chef_${count}_date")
            .apply()

        pref.edit().putInt("chef_count", count - 1).apply()

        loadChefs()
    }

    private fun openFakePayment(amount: String) {
        val intent = Intent(this, PaymentSuccessActivity::class.java)
        intent.putExtra("amount", amount)
        startActivity(intent)
    }
}
