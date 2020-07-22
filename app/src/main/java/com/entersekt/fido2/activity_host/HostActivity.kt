package com.entersekt.fido2.activity_host

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.entersekt.fido2.R
import com.entersekt.fido2.SecurityActivity
import kotlinx.android.synthetic.main.activity_host.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class HostActivity : AppCompatActivity() {

    companion object{
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "192.168.0.40"  //서버 ip
        var port = 9999
        var msg = "0"
    }

    lateinit var hostAdpater: HostAdpater
    val datas = mutableListOf<HostData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        btn_back.setOnClickListener {
            Disconnect().start()
            finish()
        }

        hostAdpater = HostAdpater(this)
        rv_host.adapter = hostAdpater
        rv_host.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        loadDatas()
    }

    fun loadDatas(){
        //소켓통신 시작
        Connect().start()
        datas.apply {
            add(
                HostData(
                    txt_HostName = "길동's Android",
                    txt_MAC = "ab:cd:ef:gh:if:aa",
                    txt_IP = "192.168.1.22"
                )
            )
            add(
                HostData(
                    txt_HostName = "철수's Iphone",
                    txt_MAC = "12:34:56:78:90:aa",
                    txt_IP = "192.168.0.2"
                )
            )
            add(
                HostData(
                    txt_HostName = "영희's Iphone",
                    txt_MAC = "12:34:56:78:90:aa",
                    txt_IP = "192.168.0.2"
                )
            )
            add(
                HostData(
                    txt_HostName = "앵두's Android",
                    txt_MAC = "12:34:56:78:90:aa",
                    txt_IP = "192.168.0.2"
                )
            )
        }
        hostAdpater.datas = datas
        hostAdpater.notifyDataSetChanged()
    }

    //비동기 소켓통신
    class Connect() :Thread(){
        val datas = mutableListOf<HostData>()

        override fun run(){
            try{
                Log.e("socket", "소켓 통신 시작")
                socket = Socket(ip, port)
                writeSocket = DataOutputStream(socket.getOutputStream())
                readSocket = DataInputStream(socket.getInputStream())

                msg = "list"
                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

                var cnt = readSocket.read()
                Log.e("count1", cnt.toString())

                for(i in 0..cnt){
                    val available = readSocket.available() // 데이터가 있으면 데이터의 사이즈 없다면 -1을 반환합니다.
                    if (available > 0){
                        val dataArr = ByteArray(available) // 사이즈에 맞게 byte array를 만듭니다.
                        readSocket.read(dataArr) // byte array에 데이터를 씁니다.
                        val data = String(dataArr) // byte array의 데이터를 통해 String을 만듭니다.
                        println(data)
                    }

                }

//                Log.e("받은 메시지", ac.toString())
//                val ac2 = readSocket.readUTF()
//                Log.e("clientSocket3", "client socket readsocket")
//                Log.e("받은 메시지", ac2.toString())


            }catch(e:Exception){    //연결 실패
                SecurityActivity.socket.close()
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
