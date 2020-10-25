package com.entersekt.fido2.ui

import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.entersekt.fido2.R
import com.entersekt.fido2.appdata.DataManage
import com.entersekt.fido2.appdata.AwsomeApp
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_information.*
import kotlinx.android.synthetic.main.activity_revise.btn_back
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.util.Base64

class InformationActivity : AppCompatActivity() {

    companion object {
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var mHandler = Handler()
        var ip = "192.168.0.254"  //서버 ip
        var port = 9999
        var msg = "0"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        Connect().start()
        btn_editpw.setOnClickListener {
            val intent = Intent(this, ReviseActivity::class.java)
            startActivity(intent)
            InformationDisconnect().start()
            finish()
        }

        btn_back.setOnClickListener {
            InformationDisconnect().start()
            finish()
        }

        if (intent.hasExtra("username")) {
            txt_User2.text = intent.getStringExtra("username")
        }

        mHandler = object : Handler(Looper.getMainLooper()) {  //Thread들로부터 Handler를 통해 메시지를 수신
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    0 -> txt_User2.text = msg.obj.toString()
                    1 -> act_info_publicIP.text = msg.obj.toString()
                    2 -> act_info_privateIP.text = msg.obj.toString()
                    3 -> act_info_subnet.text = msg.obj.toString()
                }
            }
        }

        var sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)

        var ssid = sharedPreferences.getString("ws", "AWS")
        // Base64.getDecoder().decode(encodedString)
        //var wp1 = sharedPreferences.getString("wp1", DataManage.iniPw1).toByteArray(Charsets.UTF_8)
        //var wp2 = sharedPreferences.getString("wp2", DataManage.iniPw1).toByteArray(Charsets.UTF_8)
        /*
         var wp1 = Base64.getDecoder().decode(sharedPreferences.getString("wp1", DataManage.iniPw1))
         var wp2 = Base64.getDecoder().decode(sharedPreferences.getString("wp2", DataManage.iniPw2))

         var wifiPw = AwsomeApp.decryptData(wp1, wp2)
 */
        var wifiPw = sharedPreferences.getString("wp", "awsfido2020!") // 추가

        //var wifiPw = "awsome2020!"
        //println("ssid: $ssid, pw: $wifiPw")

        var text = "WIFI:S:".plus(ssid).plus(";T:WPA;P:").plus(wifiPw).plus(";;")

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

    //비동기 소켓통신
    class Connect() : Thread() {
        override fun run() {
            try {
                Log.e("socket", "정보 소켓 통신 시작")
                socket = Socket(ip, port)
                writeSocket = DataOutputStream(socket.getOutputStream())
                readSocket = DataInputStream(socket.getInputStream())

                msg = "${DataManage.macAddress}/routerinfo"

                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

                var dataArr = ByteArray(1024) // 1024만큼 데이터 받기
                readSocket.read(dataArr) // byte array에 데이터를 씁니다.
                var data = String(dataArr).split(',')// 서버에서 보내준 한 줄 전체 - 쓰레기값 지움
                println("data : ${data}")

                for (i in 0..3) {
                    val msg = mHandler.obtainMessage()
                    msg.what = i
                    msg.obj = data[i]
                    mHandler.sendMessage(msg)
                }

            } catch (e: Exception) {    //연결 실패
                socket.close()
            }

        }
    }

    class InformationDisconnect : Thread() {
        override fun run() {
            try {
                socket.close()
                ThreadDeath()
            } catch (e: Exception) {

            }
        }
    }

}
