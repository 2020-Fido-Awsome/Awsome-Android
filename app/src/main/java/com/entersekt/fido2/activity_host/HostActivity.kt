package com.entersekt.fido2.activity_host

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.entersekt.fido2.R
import com.entersekt.fido2.SecurityActivity
import kotlinx.android.synthetic.main.activity_host.*
import kotlinx.android.synthetic.main.activity_host.btn_back
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class HostActivity : AppCompatActivity() {

    companion object{
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "172.18.21.22"  //서버 ip
        var port = 9999
        var msg = "0"
        var cnt = 0
        var data = ""
        val datas = mutableListOf<HostData>()
        lateinit var hostAdpater: HostAdpater
    }

    private var dataArr = arrayOfNulls<String>(cnt)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        datas.clear()

        btn_back.setOnClickListener {
            Disconnect().start()
            finish()
        }

        hostAdpater = HostAdpater(this)
        rv_host.adapter = hostAdpater
        rv_host.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        Connect().start()

        loadDatas()
    }

    //비동기 소켓통신
    class Connect() :Thread(){
        override fun run(){
            try{
                socket = Socket(ip, port)
                writeSocket = DataOutputStream(socket.getOutputStream())
                readSocket = DataInputStream(socket.getInputStream())

                msg = "list"
                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

                cnt = readSocket.read()

                var dataArr = ByteArray(1024) // 1024만큼 데이터 받기
                readSocket.read(dataArr) // byte array에 데이터를 씁니다.
                data = String(dataArr)
                socket.close()
            }catch(e:Exception){    //연결 실패
                SecurityActivity.socket.close()
            }

        }
    }

    class Disconnect:Thread(){
        override fun run() {
            try{
                socket.close()
                ThreadDeath()
            }catch(e:Exception){

            }
        }
    }

    fun loadDatas(){

        println("loadData : ${data}")

        for(i in 1..cnt){
            dataArr[i-1] = data.split('/')[i]
        }

        for( i in 0..cnt-1){
            println(dataArr[i])

            datas.apply {
                add(
                    HostData(
                        txt_HostName = dataArr[i]!!.split(',')[0],
                        txt_MAC = dataArr[i]!!.split(',')[1],
                        txt_IP = dataArr[i]!!.split(',')[2]
                    )
                )
            }
            hostAdpater.datas = datas
            hostAdpater.notifyDataSetChanged()

        }
        hostAdpater.notifyDataSetChanged()

    }

}
