package com.example.travel_app

import android.content.ContentValues
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class PaymentSuccessActivity : AppCompatActivity() {

    private lateinit var txtAmount: TextView
    private lateinit var txtBookingId: TextView
    private lateinit var btnInvoice: Button

    private var paidAmount = "0"
    private var bookingId = "BK0000"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        txtAmount = findViewById(R.id.txtPaidAmount)
        txtBookingId = findViewById(R.id.txtBookingId)
        btnInvoice = findViewById(R.id.btnDownloadInvoice)

        paidAmount = intent.getStringExtra("amount") ?: "0"
        bookingId = "BK" + Random.nextInt(10000, 99999)

        txtAmount.text = "Amount Paid: ₹$paidAmount"
        txtBookingId.text = "Booking ID: $bookingId"

        btnInvoice.setOnClickListener {
            savePdfToDownloadFolder()
        }
    }

    private fun savePdfToDownloadFolder() {

        val fileName = "Invoice_$bookingId.pdf"

        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
            put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Downloads.IS_PENDING, 1)
            }
        }

        val resolver = contentResolver
        val uri: Uri? = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        if (uri == null) {
            Toast.makeText(this, "Error creating file!", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val outputStream = resolver.openOutputStream(uri)

            // PDF Creation
            val pdf = android.graphics.pdf.PdfDocument()
            val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(595, 842, 1).create()
            val page = pdf.startPage(pageInfo)

            val canvas: Canvas = page.canvas
            val paint = Paint()
            paint.color = Color.BLACK

            paint.textSize = 40f
            canvas.drawText("COOKING CLASS INVOICE", 140f, 80f, paint)

            paint.textSize = 25f
            canvas.drawText("---------------------------------------------", 80f, 110f, paint)

            paint.textSize = 28f
            canvas.drawText("Booking ID: $bookingId", 80f, 180f, paint)
            canvas.drawText("Amount Paid: ₹$paidAmount", 80f, 230f, paint)
            canvas.drawText("Status: Payment Successful", 80f, 280f, paint)

            canvas.drawText("---------------------------------------------", 80f, 330f, paint)

            pdf.finishPage(page)
            pdf.writeTo(outputStream!!)
            pdf.close()
            outputStream.close()

            // Required only for Android 10+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
                resolver.update(uri, contentValues, null, null)
            }

            Toast.makeText(this, "Invoice Saved in Downloads", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Toast.makeText(this, "Error Saving Invoice!", Toast.LENGTH_SHORT).show()
        }
    }
}
