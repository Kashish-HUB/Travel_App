package com.example.travel_app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TravelSafetyActivity : AppCompatActivity() {

    private lateinit var txtAlerts: TextView
    private lateinit var btnReport: Button
    private val handler = Handler(Looper.getMainLooper())

    private val alertMessages = listOf(
        "⚠️ Heavy traffic near Old City area.",
        "⚠️ Weather Alert: Light rain expected after 6 PM.",
        "🚧 Road Closed: Bridge maintenance near Riverside.",
        "⚠️ Local protest reported near Market Square.",
        "✔ All routes currently safe to travel."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_safety)

        txtAlerts = findViewById(R.id.txtAlerts)
        btnReport = findViewById(R.id.btnReportIssue)


        startAlertUpdates()


        btnReport.setOnClickListener {
            val intent = Intent(this, ReportIssueActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startAlertUpdates() {
        handler.post(object : Runnable {
            override fun run() {
                val randomAlert = alertMessages.random()
                txtAlerts.text = randomAlert
                handler.postDelayed(this, 3000)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}
