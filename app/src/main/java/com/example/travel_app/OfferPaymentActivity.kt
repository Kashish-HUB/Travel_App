package com.example.travel_app

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class OfferPaymentActivity : AppCompatActivity() {

    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_payment)

        pref = getSharedPreferences("TravelDeals", MODE_PRIVATE)


        val txtTitle = findViewById<TextView>(R.id.txtDealTitle)
        val txtPrice = findViewById<TextView>(R.id.txtDealPrice)
        val txtWishlist = findViewById<TextView>(R.id.txtWishlistStatus)
        val btnPay = findViewById<Button>(R.id.btnPayDeal)


        val title = intent.getStringExtra("title") ?: ""
        val price = intent.getStringExtra("amount") ?: "0"



        txtTitle.text = title
        txtPrice.text = "Price: ₹$price"


        val key = "wish_${title.replace(" ", "_")}"
        val saved = pref.getBoolean(key, false)
        txtWishlist.text = if (saved) "Wishlist: Saved ❤️" else "Wishlist: Not Saved"


        btnPay.setOnClickListener {

            val i = Intent(this, successfull::class.java)

            i.putExtra("amount", price)
            i.putExtra("title", title)


            startActivity(i)
        }
    }
}
