package com.example.travel_app

import android.content.ContentValues
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val txtPackageName = findViewById<TextView>(R.id.txtPackageName)
        val txtAmount = findViewById<TextView>(R.id.txtAmount)
        val btnConfirmPay = findViewById<Button>(R.id.btnConfirmPay)
        val btnDownloadInvoice = findViewById<Button>(R.id.btnDownloadInvoice)

        val title = intent.getStringExtra("title")
        val discountPrice = intent.getStringExtra("discountPrice")

        txtPackageName.text = "Package: $title"
        txtAmount.text = "Amount to Pay: $discountPrice"

        btnConfirmPay.setOnClickListener {
            txtAmount.text = "Payment Successful ✔"
            Toast.makeText(this, "Payment Completed!", Toast.LENGTH_SHORT).show()
        }

        btnDownloadInvoice.setOnClickListener {
            generateInvoicePDF(
                packageName = title ?: "Travel Package",
                price = discountPrice ?: "0"
            )
        }
    }

    private fun generateInvoicePDF(packageName: String, price: String) {

        val pdf = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(450, 700, 1).create()
        val page = pdf.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val paint = Paint()
        paint.textSize = 16f

        canvas.drawText("TRAVEL PACKAGE INVOICE", 120f, 50f, paint)
        canvas.drawText("-------------------------------------------", 50f, 80f, paint)

        canvas.drawText("Package Name: $packageName", 50f, 130f, paint)
        canvas.drawText("Amount Paid: $price", 50f, 170f, paint)

        canvas.drawText("-------------------------------------------", 50f, 210f, paint)
        canvas.drawText("Booking Status: Confirmed ✔", 50f, 260f, paint)
        canvas.drawText("Thank you for booking with us!", 50f, 300f, paint)

        pdf.finishPage(page)

        val fileName = "Invoice_${System.currentTimeMillis()}.pdf"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                put(MediaStore.Downloads.RELATIVE_PATH, "Documents/Invoices/")
            }

            val uri = contentResolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                contentValues
            )

            val outputStream = contentResolver.openOutputStream(uri!!)!!
            pdf.writeTo(outputStream)
            outputStream.close()

        } else {
            val directory = File(Environment.getExternalStorageDirectory().absolutePath + "/Invoices")
            if (!directory.exists()) directory.mkdirs()

            val file = File(directory, fileName)
            val outputStream = FileOutputStream(file)

            pdf.writeTo(outputStream)
            outputStream.close()
        }

        pdf.close()

        Toast.makeText(this, "Invoice Downloaded Successfully!", Toast.LENGTH_LONG).show()
    }
}
