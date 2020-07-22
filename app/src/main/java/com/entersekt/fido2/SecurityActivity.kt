package com.entersekt.fido2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_security.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class SecurityActivity : AppCompatActivity() {

    companion object{
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "192.168.0.254"  //서버 ip
        var port = 9999
        var msg = "0"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security)

        btn_back.setOnClickListener {
            Disconnect().start()
            finish()
        }

        act_sec_switch_arp_spoofing.setOnClickListener {
            if(act_sec_switch_arp_spoofing.isChecked){
                Connect(1).start()
            }else Connect(0).start()
        }

    }

    class Connect(i: Int) :Thread(){
        val con = i
        override fun run(){
            try{
                socket = Socket(ip, port)
                writeSocket = DataOutputStream(socket.getOutputStream())
                readSocket = DataInputStream(socket.getInputStream())

                msg = if(con == 1){"onarp"}else "offarp"

                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

            }catch(e:Exception){    //연결 실패
                socket.close()
            }

        }
    }

    class Disconnect:Thread(){
        override fun run() {
            try{
                socket.close()
            }catch(e:Exception){

            }
        }
    }

}