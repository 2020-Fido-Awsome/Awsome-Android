package com.entersekt.fido2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.entersekt.fido2.R
import com.entersekt.fido2.appdata.DataManage
import kotlinx.android.synthetic.main.activity_reset.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class ResetActivity : AppCompatActivity() {

    companion object {
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "192.168.0.254"  //서버 ip
        var port = 9999
        var msg = "0"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)

        btn_back.setOnClickListener {
            ResetDisconnect().start()
            finish()
        }

        act_reset_btn_restart.setOnClickListener {
            Connect(1).start()
        }

        act_reset_btn_reset.setOnClickListener {
            Connect(0).start()
        }
    }

    class Connect(i: Int) : Thread() {
        val con = i
        override fun run() {
            try {
                socket = Socket(ip, port)
                writeSocket = DataOutputStream(socket.getOutputStream())
                readSocket = DataInputStream(socket.getInputStream())

                msg = if (con == 1) {
                    "${DataManage.macAddress}/restart"
                } else "${DataManage.macAddress}/reset"

                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

            } catch (e: Exception) {    //연결 실패
                socket.close()
            }

        }
    }

    class ResetDisconnect : Thread() {
        override fun run() {
            try {
                socket.close()
                ThreadDeath()
            } catch (e: Exception) {

            }
        }
    }
}
