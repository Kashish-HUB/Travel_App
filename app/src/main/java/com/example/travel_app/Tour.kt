package com.example.travel_app

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.OutputStream
import kotlin.random.Random

class Tour : AppCompatActivity() {

    private lateinit var txtAmount: TextView
    private lateinit var txtBookingId: TextView
    private lateinit var btnInvoice: Button

    private var amount = "0"
    private var bookingId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour)

        txtAmount = findViewById(R.id.txtTourPaidAmount)
        txtBookingId = findViewById(R.id.txtTourBookingId)
        btnInvoice = findViewById(R.id.btnTourDownloadInvoice)

        // Get amount from FakePayment
        amount = intent.getStringExtra("amount") ?: "0"

        // Generate unique booking id
        bookingId = "TOUR" + Random.nextInt(10000, 99999)

        txtAmount.text = "Amount Paid: ₹$amount"
        txtBookingId.text = "Booking ID: $bookingId"

        btnInvoice.setOnClickListener {
            createInvoicePDF()
        }
    }

    // ==================================================
    //              CREATE INVOICE PDF (NO PERMISSION)
    // ==================================================
    private fun createInvoicePDF() {

        val pdf = android.graphics.pdf.PdfDocument()

        val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(
            595, 842, 1
        ).create()

        val page = pdf.startPage(pageInfo)
        val canvas: Canvas = page.canvas
        val paint = Paint()

        // Title
        paint.color = Color.BLACK
        paint.textSize = 40f
        canvas.drawText("CULTURAL TOUR INVOICE", 120f, 80f, paint)

        // Line
        paint.textSize = 25f
        canvas.drawText("----------------------------------------------", 80f, 120f, paint)

        // Details
        paint.textSize = 30f
        canvas.drawText("Booking ID: $bookingId", 80f, 200f, paint)
        canvas.drawText("Amount Paid: ₹$amount", 80f, 260f, paint)
        canvas.drawText("Status: Payment Successful", 80f, 320f, paint)

        pdf.finishPage(page)

        val resolver = contentResolver

        val contentValues = android.content.ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "Tour_Invoice_$bookingId.pdf")
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/")
        }

        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        try {
            val outputStream: OutputStream? = resolver.openOutputStream(uri!!)
            pdf.writeTo(outputStream)
            pdf.close()
            outputStream?.close()

            Toast.makeText(this, "Invoice saved in Downloads folder", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Failed to save invoice!", Toast.LENGTH_SHORT).show()
        }
    }
}
