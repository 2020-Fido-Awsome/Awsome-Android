package com.entersekt.fido2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.entersekt.fido2.activity_admin.AdminActivity
import com.entersekt.fido2.activity_host.HostActivity
import com.entersekt.fido2.data.AwsomeApp
import com.entersekt.fido2.data.DataManage
import kotlinx.android.synthetic.main.activity_main.*
import java.net.NetworkInterface
import java.util.*
import com.entersekt.fido2.KeyStore.Companion


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btn_Host.setOnClickListener {
            val intent = Intent(this, HostActivity::class.java)
            startActivity(intent)
        }
        btn_infromation.setOnClickListener {
            val intent = Intent(this, InformationActivity::class.java)
            startActivity(intent)
        }
        btn_security.setOnClickListener {
            val intent = Intent(this, SecurityActivity::class.java)
            startActivity(intent)
        }
        btn_log.setOnClickListener {
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
        }
        btn_reset.setOnClickListener {
            val intent = Intent(this, ResetActivity::class.java)
            startActivity(intent)
        }
        btn_admin.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }


        val macAddress = getMACAddress("wlan0")
        DataManage.macAddress = macAddress



        println("################dddd#########3")

//        try {
//            var a = KeyStore.exist
//            println("~~~~~~~~~~~~~~~~~~")
//        } catch (e:ExceptionInInitializerError){ // NoClassDefFoundError
//            println("@@@@${KeyStore.exist}@@@@")
//        }

        println("@@@@${KeyStore.exist}@@@@")
        if (KeyStore.exist) {
            KeyStore.genKey()
            KeyStore.exist = true
            println("****${KeyStore.exist}****")
        }






    }
    var lastTimeBackPressed:Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed >= 2000) {
            lastTimeBackPressed = System.currentTimeMillis()
            Toast.makeText(this,"버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show()
        } else {
            finish()
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


}
