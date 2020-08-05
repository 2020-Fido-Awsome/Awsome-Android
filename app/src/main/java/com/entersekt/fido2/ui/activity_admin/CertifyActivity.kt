package com.entersekt.fido2.activity_admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.entersekt.fido2.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_certify.*


class CertifyActivity : AppCompatActivity() {

    var text = "Awsome FIDO Alliance, Good Bye Password!\nPlease give first place to AWS team."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_certify)

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
