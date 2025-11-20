package com.example.travel_app

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class BookFlightActivity : AppCompatActivity() {

    private val allFlights = mutableListOf<Flight>()
    private var currentResults = listOf<Flight>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_flight)

        // Views
        val etFrom = findViewById<EditText>(R.id.etFrom)
        val etTo = findViewById<EditText>(R.id.etTo)
        val btnDepart = findViewById<Button>(R.id.btnDepart)
        val btnReturn = findViewById<Button>(R.id.btnReturn)
        val spinnerPassengers = findViewById<Spinner>(R.id.spinnerPassengers)
        val spinnerClass = findViewById<Spinner>(R.id.spinnerClass)
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val tvResultsHeader = findViewById<TextView>(R.id.tvResultsHeader)
        val rvFlights = findViewById<RecyclerView>(R.id.rvFlights)

        setupSpinners(spinnerPassengers, spinnerClass)
        seedDummyData()

        btnDepart.setOnClickListener { pickDate(btnDepart) }
        btnReturn.setOnClickListener { pickDate(btnReturn) }

        btnSearch.setOnClickListener {
            val from = etFrom.text.toString().trim()
            val to = etTo.text.toString().trim()

            if (from.isEmpty() || to.isEmpty()) {
                Toast.makeText(this, "Please enter both From and To", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (from.equals(to, ignoreCase = true)) {
                Toast.makeText(this, "From and To cannot be same", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            performSearch(from, to, tvResultsHeader, rvFlights)
        }
    }

    private fun setupSpinners(passengerSpinner: Spinner, classSpinner: Spinner) {
        val passengers = listOf("1 passenger", "2 passengers", "3 passengers", "4 Passengers",
            "5 Passengers", "6 Passengers", "7 Passengers", "8 Passengers",
            "9 Passengers", "10 Passengers")
        val classes = listOf("Economy", "Premium Economy", "Business")

        val pAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, passengers)
        pAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        passengerSpinner.adapter = pAdapter

        val cAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, classes)
        cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        classSpinner.adapter = cAdapter
    }

    private fun pickDate(button: View) {
        val c = Calendar.getInstance()
        val y = c.get(Calendar.YEAR)
        val m = c.get(Calendar.MONTH)
        val d = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val display = "${dayOfMonth}/${month + 1}/${year}"
            (button as? Button)?.text = display
        }, y, m, d).show()
    }

    private fun seedDummyData() {

        allFlights.clear()

        // --- NORTH INDIA ---
        allFlights.add(Flight("F001", "IndiGo", "Delhi (DEL)", "Mumbai (BOM)", "07:00", "09:00", "2h", 0, 4999))
        allFlights.add(Flight("F002", "Air India", "Delhi (DEL)", "Kolkata (CCU)", "08:00", "10:10", "2h 10m", 0, 5200))
        allFlights.add(Flight("F003", "Vistara", "Delhi (DEL)", "Chennai (MAA)", "06:30", "09:00", "2h 30m", 0, 5500))
        allFlights.add(Flight("F004", "SpiceJet", "Delhi (DEL)", "Bengaluru (BLR)", "09:00", "11:10", "2h 10m", 0, 5300))
        allFlights.add(Flight("F005", "IndiGo", "Delhi (DEL)", "Goa (GOI)", "10:30", "12:30", "2h", 0, 4800))

        // --- MAHARASHTRA ---
        allFlights.add(Flight("F006", "Air India", "Mumbai (BOM)", "Delhi (DEL)", "11:00", "13:00", "2h", 0, 5100))
        allFlights.add(Flight("F007", "SpiceJet", "Mumbai (BOM)", "Goa (GOI)", "13:00", "14:10", "1h 10m", 0, 3200))
        allFlights.add(Flight("F008", "Vistara", "Mumbai (BOM)", "Jaipur (JAI)", "14:30", "16:10", "1h 40m", 0, 5600))
        allFlights.add(Flight("F009", "IndiGo", "Mumbai (BOM)", "Kolkata (CCU)", "15:00", "17:25", "2h 25m", 0, 6200))

        // --- KARNATAKA ---
        allFlights.add(Flight("F010", "IndiGo", "Bengaluru (BLR)", "Delhi (DEL)", "07:45", "10:10", "2h 25m", 0, 5200))
        allFlights.add(Flight("F011", "Air India", "Bengaluru (BLR)", "Chennai (MAA)", "09:00", "10:00", "1h", 0, 2800))
        allFlights.add(Flight("F012", "Vistara", "Bengaluru (BLR)", "Hyderabad (HYD)", "12:00", "13:00", "1h", 0, 2600))
        allFlights.add(Flight("F013", "SpiceJet", "Bengaluru (BLR)", "Goa (GOI)", "17:00", "18:20", "1h 20m", 0, 3400))

        // --- TAMIL NADU ---
        allFlights.add(Flight("F014", "IndiGo", "Chennai (MAA)", "Delhi (DEL)", "06:30", "09:15", "2h 45m", 0, 5500))
        allFlights.add(Flight("F015", "Air India", "Chennai (MAA)", "Bengaluru (BLR)", "10:00", "11:00", "1h", 0, 3000))
        allFlights.add(Flight("F016", "Vistara", "Chennai (MAA)", "Kolkata (CCU)", "14:00", "16:00", "2h", 0, 4900))
        allFlights.add(Flight("F017", "SpiceJet", "Chennai (MAA)", "Hyderabad (HYD)", "18:00", "19:00", "1h", 0, 3100))

        // --- WEST BENGAL ---
        allFlights.add(Flight("F018", "IndiGo", "Kolkata (CCU)", "Delhi (DEL)", "06:00", "08:10", "2h 10m", 0, 5400))
        allFlights.add(Flight("F019", "Air India", "Kolkata (CCU)", "Mumbai (BOM)", "09:00", "11:30", "2h 30m", 0, 5900))
        allFlights.add(Flight("F020", "Vistara", "Kolkata (CCU)", "Chennai (MAA)", "13:30", "15:45", "2h 15m", 0, 5100))
        allFlights.add(Flight("F021", "SpiceJet", "Kolkata (CCU)", "Hyderabad (HYD)", "17:00", "19:00", "2h", 0, 4500))

        // --- GUJARAT ---
        allFlights.add(Flight("F022", "IndiGo", "Ahmedabad (AMD)", "Delhi (DEL)", "08:00", "09:30", "1h 30m", 0, 4200))
        allFlights.add(Flight("F023", "Air India", "Ahmedabad (AMD)", "Mumbai (BOM)", "12:00", "13:10", "1h 10m", 0, 3500))
        allFlights.add(Flight("F024", "SpiceJet", "Ahmedabad (AMD)", "Goa (GOI)", "19:00", "20:45", "1h 45m", 0, 3800))

        // --- RAJASTHAN ---
        allFlights.add(Flight("F025", "IndiGo", "Jaipur (JAI)", "Delhi (DEL)", "10:30", "11:30", "1h", 0, 3100))
        allFlights.add(Flight("F026", "Air India", "Jaipur (JAI)", "Mumbai (BOM)", "15:00", "16:40", "1h 40m", 0, 4500))

        // --- PUNJAB ---
        allFlights.add(Flight("F027", "IndiGo", "Amritsar (ATQ)", "Delhi (DEL)", "08:30", "09:30", "1h", 0, 3000))
        allFlights.add(Flight("F028", "Vistara", "Amritsar (ATQ)", "Mumbai (BOM)", "12:00", "14:10", "2h 10m", 0, 5200))

        // --- TELANGANA ---
        allFlights.add(Flight("F029", "IndiGo", "Hyderabad (HYD)", "Delhi (DEL)", "06:30", "08:40", "2h 10m", 0, 5000))
        allFlights.add(Flight("F030", "Air India", "Hyderabad (HYD)", "Bengaluru (BLR)", "11:30", "12:30", "1h", 0, 2500))
        allFlights.add(Flight("F031", "SpiceJet", "Hyderabad (HYD)", "Chennai (MAA)", "16:00", "17:00", "1h", 0, 2800))

        // --- GOA ---
        allFlights.add(Flight("F032", "IndiGo", "Goa (GOI)", "Mumbai (BOM)", "09:30", "10:40", "1h 10m", 0, 3300))
        allFlights.add(Flight("F033", "SpiceJet", "Goa (GOI)", "Delhi (DEL)", "13:00", "15:20", "2h 20m", 0, 4800))
    }


    private fun performSearch(from: String, to: String, tvResultsHeader: TextView, rvFlights: RecyclerView) {
        val fLower = from.toLowerCase(Locale.getDefault())
        val tLower = to.toLowerCase(Locale.getDefault())

        currentResults = allFlights.filter {
            it.fromCity.toLowerCase(Locale.getDefault()).contains(fLower) &&
                    it.toCity.toLowerCase(Locale.getDefault()).contains(tLower)
        }

        if (currentResults.isEmpty()) {
            tvResultsHeader.visibility = View.VISIBLE
            tvResultsHeader.text = "No flights found for $from → $to (India-only dummy data)"
            tvResultsHeader.setTextColor(Color.parseColor("#D32F2F"))
            rvFlights.visibility = View.GONE
                    return
        }

        tvResultsHeader.visibility = View.VISIBLE
        tvResultsHeader.text = "Found ${currentResults.size} flights"
        tvResultsHeader.setTextColor(Color.parseColor("#2E7D32"))
        rvFlights.visibility = View.VISIBLE
        rvFlights.layoutManager = LinearLayoutManager(this)
        rvFlights.adapter = FlightAdapter(currentResults) { flight ->
            val i = Intent(this, BookingActivity::class.java)
            i.putExtra("flight", flight)
            startActivity(i)
        }
    }
}
