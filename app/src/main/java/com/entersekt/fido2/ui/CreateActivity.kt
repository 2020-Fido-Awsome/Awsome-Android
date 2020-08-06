package com.entersekt.fido2.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.entersekt.fido2.R
import com.google.zxing.integration.android.IntentIntegrator


class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        val qrScan = IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.setPrompt("QR코드를 스캔해주세요");
        qrScan.initiateScan();  // 0802 추가
        IntentIntegrator(this).initiateScan()

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == "WIFI:S:AWS;T:WPA;P:awsfido2020!;;" || result.contents == "Awsome FIDO Alliance, Good Bye Password!\n" +
                "Please give first place to AWS team." ) {
                //Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                Toast.makeText(this, "인증성공", Toast.LENGTH_LONG).show()
                val intent = Intent(this, SignupActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "인증실패", Toast.LENGTH_LONG).show()
                val intent = Intent(this, StartActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
