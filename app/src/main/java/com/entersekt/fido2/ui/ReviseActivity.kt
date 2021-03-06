package com.entersekt.fido2.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.entersekt.fido2.R
import com.entersekt.fido2.appdata.AwsomeApp
import com.entersekt.fido2.appdata.DataManage
import kotlinx.android.synthetic.main.activity_host.btn_back
import kotlinx.android.synthetic.main.activity_revise.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.util.*


class ReviseActivity : AppCompatActivity() {

    companion object {
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "192.168.0.254"  //서버 ip
        var port = 9999
        var msg = "0"
    }

    var ssid = ""
    var pwd = ""

    var editor = DataManage.pref.edit()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revise)

        btn_back.setOnClickListener {
            ReviseDisconnect().start()
            finish()
        }

        btn_revise.setOnClickListener {
            if (txt_newSsid.text.isNullOrBlank() || txt_pwd.text.isNullOrBlank() || txt_repwd.text.isNullOrBlank()) {
                Toast.makeText(this, "아이디 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                if (txt_pwd.text.toString() == txt_repwd.text.toString()) {
                    ssid = txt_newSsid.text.toString()
                    pwd = txt_pwd.text.toString()

/*
                    var temPw = AwsomeApp.encryptData(pwd)

                    editor.putString("ws", ssid)
                    editor.putString("wp1" , Base64.getEncoder().encodeToString(temPw.first))
                    editor.putString("wp2", Base64.getEncoder().encodeToString(temPw.second))
                    editor.commit()
                    */
                    DataManage.ws = ssid
                    DataManage.wp = pwd
                    // 2줄 추가


                    Connect(ssid, pwd).start()

                    val intent = Intent(this, InformationActivity::class.java)
                    intent.putExtra("username", ssid)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    class Connect(ssid: String, pwd: String) : Thread() {
        val ssid = ssid
        val pwd = pwd
        override fun run() {
            try {
                socket = Socket(ip, port)
                writeSocket = DataOutputStream(socket.getOutputStream())
                readSocket = DataInputStream(socket.getInputStream())

                msg = "${DataManage.macAddress}/changeinfo/${ssid}/${pwd}"

                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

            } catch (e: Exception) {    //연결 실패
                SecurityActivity.socket.close()
            }

        }
    }

    class ReviseDisconnect : Thread() {
        override fun run() {
            try {
                socket.close()
                ThreadDeath()
            } catch (e: Exception) {

            }
        }
    }
}
