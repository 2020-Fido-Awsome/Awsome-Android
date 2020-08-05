package com.entersekt.fido2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.entersekt.fido2.R
import com.entersekt.fido2.appdata.DataManage
import java.net.NetworkInterface
import java.util.*

class SplashActivity : AppCompatActivity() {

    val SPLASH_VIEW_TIME: Long = 2000 //2초간 스플래시 화면을 보여줌 (ms)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val macAddress = getMACAddress("wlan0")
        DataManage.macAddress = macAddress

        Handler().postDelayed({ //delay를 위한 handler
            startActivity(Intent(this, StartActivity::class.java))
            finish()
        }, SPLASH_VIEW_TIME)
    }
}

private fun getMACAddress(interfaceName: String?): String {
    try {
        val interfaces: List<NetworkInterface> =
            Collections.list(NetworkInterface.getNetworkInterfaces())
        for (intf in interfaces) {
            if (interfaceName != null) {
                if (!intf.name.equals(interfaceName, ignoreCase = true)) continue
            }
            val mac: ByteArray = intf.hardwareAddress ?: return ""
            val buf = StringBuilder()
            for (idx in mac.indices) buf.append(String.format("%02X:", mac[idx]))
            if (buf.length > 0) buf.deleteCharAt(buf.length - 1)
            return buf.toString()
        }
    } catch (ex: Exception) {
    } // for now eat exceptions
    return ""
}