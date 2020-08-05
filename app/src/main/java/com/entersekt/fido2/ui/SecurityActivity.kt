package com.entersekt.fido2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.entersekt.fido2.R
import com.entersekt.fido2.appdata.AwsomeApp
import com.entersekt.fido2.appdata.DataManage
import kotlinx.android.synthetic.main.activity_security.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.util.*


class SecurityActivity : AppCompatActivity() {

    var str = ""

    companion object {
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "192.168.0.254"  //서버 ip
        var port = 9999
        var msg = "0"
        var data = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security)

        StartConnect().start()

        btn_back.setOnClickListener {
            SecurityDisconnect().start()
            finish()
        }

        act_sec_switch_arp_spoofing.setOnClickListener {
            if (act_sec_switch_arp_spoofing.isChecked) {
                Connect(1).start()
            } else Connect(0).start()
        }

        act_sec_switch_syn_flooding.setOnClickListener {
            if (act_sec_switch_syn_flooding.isChecked) {
                Connect(3).start()
            } else Connect(2).start()
        }

        act_sec_switch_ddos.setOnClickListener {
            if (act_sec_switch_ddos.isChecked) {
                Connect(5).start()
            } else Connect(4).start()
        }

        act_sec_switch_dns_spoofing.setOnClickListener {
            if (act_sec_switch_dns_spoofing.isChecked) {
                Connect(7).start()
            } else Connect(6).start()
        }

        act_sec_switch_command_injection.setOnClickListener {
            if (act_sec_switch_command_injection.isChecked) {
                Connect(9).start()
            } else Connect(8).start()
        }

        act_sec_switch_qr.setOnClickListener {
            if (act_sec_switch_qr.isChecked) {
                Connect(11).start()
            } else Connect(10).start()
        }

        if (!data.isNullOrEmpty()) {
            setStatus()
        }
    }

    class StartConnect : Thread() {
        override fun run() {
            try {
                socket = Socket(ip, port)
                writeSocket = DataOutputStream(socket.getOutputStream())
                readSocket = DataInputStream(socket.getInputStream())

                msg = "status"
                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

                var dataArr = ByteArray(1024) // 1024만큼 데이터 받기
                readSocket.read(dataArr) // byte array에 데이터를 씁니다.
                data = String(dataArr)// 서버에서 보내준 한 줄 전체 - 쓰레기값 지움
                println("data : $data")

            } catch (e: Exception) {    //연결 실패
                socket.close()
            }
        }
    }

    class Connect(i: Int) : Thread() {
        private val con = i
        override fun run() {
            try {
                socket = Socket(ip, port)
                writeSocket = DataOutputStream(socket.getOutputStream())
                readSocket = DataInputStream(socket.getInputStream())

                when (con) {
                    0 -> msg = DataManage.macAddress.plus("/offarp")
                    1 -> msg = DataManage.macAddress.plus("/onarp")
                    2 -> msg = DataManage.macAddress.plus("/offsyn")
                    3 -> msg = DataManage.macAddress.plus("/onsyn")
                    4 -> msg = DataManage.macAddress.plus("/offdos")
                    5 -> msg = DataManage.macAddress.plus("/ondos")
                    6 -> msg = DataManage.macAddress.plus("/offdns")
                    7 -> msg = DataManage.macAddress.plus("/ondns")
                    8 -> msg = DataManage.macAddress.plus("/offcmd")
                    9 -> msg = DataManage.macAddress.plus("/oncmd")
                    10 -> msg = DataManage.macAddress.plus("/offqr")
                    11 -> msg = DataManage.macAddress.plus("/onqr/").plus(genRandom())
                }
//                msg = if(con == 1){"onarp"}else "offarp"

                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

            } catch (e: Exception) {    //연결 실패
                socket.close()
            }
        }

        private fun genRandom() :String {
            var random = Random()
            var str = ""

            str = str.plus((random.nextInt()+random.nextFloat()).toString())
            str = str.plus((random.nextInt()+random.nextFloat()).toString())
//        for(i in 1..10) {
//            str = str.plus((random.nextInt()+random.nextFloat()).toString())
//        }

            var editor = DataManage.pref.edit()
            var temPw = AwsomeApp.encryptData(str)
            editor.putString("wp1" , Base64.getEncoder().encodeToString(temPw.first))
            editor.putString("wp2", Base64.getEncoder().encodeToString(temPw.second))

            return str
        }
    }

    class SecurityDisconnect : Thread() {
        override fun run() {
            try {
                socket.close()
                ThreadDeath()
            } catch (e: Exception) {

            }
        }
    }

    private fun setStatus() {
        println("data!!!! : $data")

        act_sec_switch_arp_spoofing.isChecked = (data.split(',')[1] == "on")
        act_sec_switch_syn_flooding.isChecked = (data.split(',')[2] == "on")
        act_sec_switch_ddos.isChecked = data.split(',')[3] == "on"
        act_sec_switch_dns_spoofing.isChecked = data.split(',')[4] == "on"
        act_sec_switch_command_injection.isChecked = data.split(',')[5] == "on"
        act_sec_switch_qr.isChecked = data.split(',')[6] == "on"

    }

}