package com.example.travel_app

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class OfferActivity : AppCompatActivity() {

    private var selectedOffer: OfferModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer)

        val card1 = findViewById<LinearLayout>(R.id.card1)
        val card2 = findViewById<LinearLayout>(R.id.card2)
        val card3 = findViewById<LinearLayout>(R.id.card3)
        val card4 = findViewById<LinearLayout>(R.id.card4)
        val card5 = findViewById<LinearLayout>(R.id.card5)
        val card6 = findViewById<LinearLayout>(R.id.card6)
        val card7 = findViewById<LinearLayout>(R.id.card7)
        val card8 = findViewById<LinearLayout>(R.id.card8)
        val card9 = findViewById<LinearLayout>(R.id.card9)
        val card10 = findViewById<LinearLayout>(R.id.card10)

        val btnGoToSaved = findViewById<Button>(R.id.btnGoToSaved)

        card1.setOnClickListener {
            selectedOffer = OfferModel("Goa Tour Premium", "₹28,000", "₹18,999", R.drawable.img_33)
        }

        card2.setOnClickListener {
            selectedOffer = OfferModel("Kashmir Paradise Tour", "₹35,000", "₹25,499", R.drawable.img_34)
        }

        card3.setOnClickListener {
            selectedOffer = OfferModel("World Tour Special", "₹1,80,000", "₹1,40,000", R.drawable.img_35)
        }

        card4.setOnClickListener {
            selectedOffer = OfferModel("Paris Romantic Package", "₹1,20,000", "₹95,000", R.drawable.img_36)
        }

        card5.setOnClickListener {
            selectedOffer = OfferModel("Dubai Gold Experience", "₹70,000", "₹54,999", R.drawable.img_37)
        }

        card6.setOnClickListener {
            selectedOffer = OfferModel("Kerala Houseboat Trip", "₹32,000", "₹24,999", R.drawable.img_38)
        }

        card7.setOnClickListener {
            selectedOffer = OfferModel("Manali Snow Adventure", "₹20,000", "₹14,499", R.drawable.img_39)
        }

        card8.setOnClickListener {
            selectedOffer = OfferModel("Thailand Budget Trip", "₹50,000", "₹36,000", R.drawable.img_40)
        }

        card9.setOnClickListener {
            selectedOffer = OfferModel("Bali Couple Retreat", "₹80,000", "₹61,999", R.drawable.img_41)
        }

        card10.setOnClickListener {
            selectedOffer = OfferModel("Singapore Family Package", "₹60,000", "₹44,999", R.drawable.img_42)
        }

        btnGoToSaved.setOnClickListener {
            if (selectedOffer == null) {
                return@setOnClickListener
            }

            val intent = Intent(this, OfferSummaryActivity::class.java)
            intent.putExtra("title", selectedOffer!!.title)
            intent.putExtra("original", selectedOffer!!.originalPrice)
            intent.putExtra("discount", selectedOffer!!.discountPrice)
            intent.putExtra("image", selectedOffer!!.imageRes)
            startActivity(intent)
        }
    }
}
