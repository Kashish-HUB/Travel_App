package com.example.travel_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class IssueSubmittedActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_submitted)

        val txtMsg: TextView = findViewById(R.id.txtMessage)
        txtMsg.text = "Issue Submitted Successfully!"

        val txtSub: TextView = findViewById(R.id.txtSubMessage)
        txtSub.text = "We Will Reach Shortly."
    }
}
