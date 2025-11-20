package com.example.travel_app

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

class successfull : AppCompatActivity() {

    private lateinit var txtAmount: TextView
    private lateinit var txtBookingId: TextView
    private lateinit var btnInvoice: Button
    private lateinit var btnHome: Button

    private var amount = "0"
    private var bookingId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_successfull)

        txtAmount = findViewById(R.id.txtPaidAmount)
        txtBookingId = findViewById(R.id.txtBookingId)
        btnInvoice = findViewById(R.id.btnDownloadInvoice)
        btnHome = findViewById(R.id.btnBackHome)


        amount = intent.getStringExtra("amount") ?: "0"


        bookingId = "TD" + Random.nextInt(10000, 99999)

        txtAmount.text = "Paid: ₹$amount"
        txtBookingId.text = "Booking ID: $bookingId"


        btnInvoice.setOnClickListener {
            generateInvoice()
        }


        btnHome.setOnClickListener {
            finish()
        }
    }

    private fun generateInvoice() {

        val pdfFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "Travel_Invoice_$bookingId.pdf"
        )

        try {
            val fos = FileOutputStream(pdfFile)

            val pdf = android.graphics.pdf.PdfDocument()
            val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(595, 842, 1).create()
            val page = pdf.startPage(pageInfo)

            val canvas: Canvas = page.canvas
            val paint = Paint()

            paint.color = Color.BLACK
            paint.textSize = 38f

            canvas.drawText("TRAVEL DEAL INVOICE", 140f, 80f, paint)

            paint.textSize = 26f
            canvas.drawText("--------------------------------------", 80f, 110f, paint)

            canvas.drawText("Booking ID : $bookingId", 80f, 180f, paint)
            canvas.drawText("Amount Paid : ₹$amount", 80f, 230f, paint)
            canvas.drawText("Payment Status : Successful", 80f, 280f, paint)

            pdf.finishPage(page)
            pdf.writeTo(fos)
            pdf.close()
            fos.close()

            Toast.makeText(this, "Invoice Saved in Downloads", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Invoice Failed!", Toast.LENGTH_SHORT).show()
        }
    }
}
