package com.entersekt.fido2.fragment_tutorial

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.entersekt.fido2.R
import com.entersekt.fido2.appdata.DataManage
import com.entersekt.fido2.data.ResponseData
import com.entersekt.fido2.ui.MainActivity
import com.entersekt.fido2.ui.ResistActivity
import com.entersekt.fido2.ui.TutorialActivity
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.fragment_tutorial_fifth.*
import kotlinx.android.synthetic.main.fragment_tutorial_third.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import java.security.MessageDigest
import java.time.temporal.TemporalUnit

class FifthFragment : Fragment() {
    companion object {
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "192.168.0.254"  //서버 ip
        var port = 9999
        var msg = "0"
        var nick = "defaultNickName"
        lateinit var responseData: ResponseData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        nick_sub_btn.setOnClickListener(View.OnClickListener {
//            val intent = Intent(activity, MainActivity::class.java)
//            startActivity(intent)
//        })


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutorial_fifth, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nick_sub_btn.setOnClickListener {

                activity?.let {
                    if (text_nick.text.isNullOrBlank()) {
                        Toast.makeText(context, "닉네임은 필수입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        nick = text_nick.text.toString()
                        DataManage.nickName = nick
                        println("##test##\nnick : ${DataManage.nickName}")
                        DataManage.macAddress = shaEncrypt(nick.plus(DataManage.mac))
                        println("sha전 : ${nick.plus(DataManage.mac)}")
                        println("sha : ${DataManage.macAddress}")
                        SigninConnect(nick).start()
                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
            }
        }

    }

        class SigninConnect(nick: String) : Thread() {
        private val usernick = nick
        override fun run() {
            try {
                socket = Socket(ip, port)
                writeSocket = DataOutputStream(socket.getOutputStream())
                readSocket = DataInputStream(socket.getInputStream())

                msg = "${DataManage.macAddress}/setinfo/${usernick}"
                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송
                socket.close()
            } catch (e: Exception) {    //연결 실패
                socket.close()
            }

        }
    }

    fun shaEncrypt(data: String): String {
        val strHash = MessageDigest.getInstance("SHA-256")
            .digest(data.toByteArray())
            .joinToString(separator = "") {
                "%02x".format(it)
            }
        return strHash
    }


}