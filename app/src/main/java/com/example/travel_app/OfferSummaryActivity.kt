package com.example.travel_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OfferSummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_summary)

        val imgSummary = findViewById<ImageView>(R.id.imgSummary)
        val txtTitle = findViewById<TextView>(R.id.txtSummaryTitle)
        val txtOriginal = findViewById<TextView>(R.id.txtSummaryOriginal)
        val txtDiscount = findViewById<TextView>(R.id.txtSummaryDiscount)
        val btnPayNow = findViewById<Button>(R.id.btnPayNow)

        val title = intent.getStringExtra("title")
        val original = intent.getStringExtra("original")
        val discount = intent.getStringExtra("discount")
        val image = intent.getIntExtra("image", 0)

        txtTitle.text = title
        txtOriginal.text = "Original Price: $original"
        txtDiscount.text = "Discount Price: $discount"
        imgSummary.setImageResource(image)

        btnPayNow.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("discountPrice", discount)
            startActivity(intent)
        }
    }
}
