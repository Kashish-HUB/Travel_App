package com.example.travel_app

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ArtisanFakePaymentActivity : AppCompatActivity() {

    private lateinit var txtAmount: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnPay: Button

    private var amount = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artisan_fake_payment)

        // Get amount from ArtisanSummaryActivity
        amount = intent.getStringExtra("amount") ?: "0"

        // Find Views
        txtAmount = findViewById(R.id.txtFakeAmount)
        radioGroup = findViewById(R.id.radioGroupApps)
        btnPay = findViewById(R.id.btnMakeFakePayment)

        // Show Amount
        txtAmount.text = "Amount: ₹$amount"

        // When Pay Button Is Clicked
        btnPay.setOnClickListener {

            val selected = radioGroup.checkedRadioButtonId

            if (selected == -1) {
                Toast.makeText(this, "Please select a UPI App", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Processing Payment...", Toast.LENGTH_SHORT).show()

            // Delay 1.2 sec → then go to success page
            btnPay.postDelayed({
                val intent = Intent(this, pay::class.java)
                intent.putExtra("amount", amount)
                startActivity(intent)
                finish()
            }, 1200)
        }
    }
}
