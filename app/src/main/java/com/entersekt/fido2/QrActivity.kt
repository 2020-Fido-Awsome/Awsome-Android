package com.entersekt.fido2

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_qr.*
import com.entersekt.fido2.KeyStore.Companion


class QrActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        var ssid = KeyStore.decryptData(KeyStore.ws.first,KeyStore.ws.second)
        var wifiPw = KeyStore.decryptData(KeyStore.wp.first,KeyStore.wp.second)
        println("ssid: $ssid, pw: $wifiPw")

        var text = "WIFI:S:".plus(ssid).plus(";T:WPA;P:").plus(wifiPw).plus(";;")

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