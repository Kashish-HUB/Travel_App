package com.example.travel_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.travel_app.R

class ReportIssueActivity : AppCompatActivity() {

    private lateinit var spinnerIssueType: Spinner
    private lateinit var spinnerCity: Spinner
    private lateinit var edtDescription: EditText
    private lateinit var edtLocation: EditText
    private lateinit var btnUploadPhoto: Button
    private lateinit var btnSubmitIssue: Button
    private lateinit var previewImage: ImageView

    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_issue)


        spinnerIssueType = findViewById(R.id.spinnerIssueType)
        spinnerCity = findViewById(R.id.spinnerCity)
        edtDescription = findViewById(R.id.edtIssueDescription)
        edtLocation = findViewById(R.id.edtLocation)
        btnUploadPhoto = findViewById(R.id.btnUploadPhoto)
        btnSubmitIssue = findViewById(R.id.btnSubmitIssue)
        previewImage = findViewById(R.id.previewImage)


        val issues = listOf(
            "Select Issue Type",
            "Fire Hazard",
            "Road Accident",
            "Suspicious Activity",
            "Harassment",
            "Medical Emergency",
            "Other"
        )

        val issueAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            issues
        )
        spinnerIssueType.adapter = issueAdapter


        val cityList = listOf(
            "Select City",
            "Delhi", "Mumbai", "Kolkata", "Chennai", "Bengaluru",
            "Hyderabad", "Pune", "Jaipur", "Chandigarh", "Lucknow",
            "Ahmedabad", "Surat", "Indore", "Patna", "Nagpur",
            "Gurgaon", "Noida", "Amritsar", "Guwahati", "Bhopal",
            "Visakhapatnam", "Kota", "Varanasi", "Ranchi"
        )

        val cityAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            cityList
        )
        spinnerCity.adapter = cityAdapter


        btnUploadPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }


        btnSubmitIssue.setOnClickListener {

            val selectedIssue = spinnerIssueType.selectedItem.toString()
            val selectedCity = spinnerCity.selectedItem.toString()
            val description = edtDescription.text.toString()
            val location = edtLocation.text.toString()

            if (selectedIssue == "Select Issue Type") {
                Toast.makeText(this, "Please select issue type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedCity == "Select City") {
                Toast.makeText(this, "Please select a city", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (description.isEmpty()) {
                Toast.makeText(this, "Please describe the issue", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (location.isEmpty()) {
                Toast.makeText(this, "Please enter location or landmark", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val intent = Intent(this, IssueSubmittedActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val imageUri = data.data
            previewImage.setImageURI(imageUri)
            previewImage.visibility = View.VISIBLE
        }
    }
}
