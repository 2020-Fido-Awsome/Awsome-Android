package com.entersekt.fido2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.entersekt.fido2.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_qr.*

class QrActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        var text = "WIFI:S:AWS;T:WPA;P:awsfido2020!;;"

        setContentView(R.layout.activity_qr);

        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix =
                multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            qrcode.setImageBitmap(bitmap)
        } catch (e: Exception) {
        }
    }
}