package com.entersekt.fido2.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.entersekt.fido2.R
import com.entersekt.fido2.appdata.DataManage
import kotlinx.android.synthetic.main.activity_store.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class SignupActivity : AppCompatActivity() {

    companion object {
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "192.168.0.254"  //서버 ip
        var port = 9999
        var msg = "0"
    }

    var usernick = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

//        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


        btn_put.setOnClickListener {
            if(text_defaultUserName.text.isNullOrBlank() || txt_defaultpwd.text.isNullOrBlank() || txt_ckpwd.text.isNullOrBlank()){
                Toast.makeText(this, "닉네임과 비밀번호가 입력되었는지 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                if(txt_defaultpwd.text.toString().equals(txt_ckpwd.text.toString())){
//                    inputMethodManager.hideSoftInputFromWindow(txt_ckpwd.getWindowToken(), 0);
                    if(txt_defaultpwd.length()<= 8 ){
                        Toast.makeText(this, "비밀번호 길이를  8보다 크게 입력해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        usernick = editTextTextPersonName.text.toString()

                        val intent = Intent(this, ResistActivity::class.java)
                        intent.putExtra("nick", usernick)
                        startActivity(intent)
                        finish()
                    }

                }else{

                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    class StoreConnect(nick: String) :Thread(){
        val usernick = nick
        override fun run(){
            try{
                socket = Socket(ip, port)
                writeSocket = DataOutputStream(socket.getOutputStream())
                readSocket = DataInputStream(socket.getInputStream())

                msg = "${DataManage.macAddress}/setinfo/${usernick}"

                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

                socket.close()
            }catch(e:Exception){    //연결 실패
                socket.close()
            }

        }
    }

//    class StoreDisconnect:Thread(){
//        override fun run() {
//            try{
//                socket.close()
//                ThreadDeath()
//            }catch(e:Exception){
//
//            }
//        }
//    }
}
