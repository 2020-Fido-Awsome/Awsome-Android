package com.entersekt.fido2.activity_host

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.entersekt.fido2.R
import kotlinx.android.synthetic.main.activity_host.*
import kotlinx.android.synthetic.main.activity_host.btn_back
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class HostActivity : AppCompatActivity() {

    companion object {
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "192.168.1.167"  //서버 ip
        var port = 9999
        var msg = "0"
        var cnt = 0
        var data = ""
        val datas = mutableListOf<HostData>()
        lateinit var hostAdpater: HostAdpater
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        datas.clear()

        btn_back.setOnClickListener {
            HostDisconnect().start()
            finish()
        }

        hostAdpater = HostAdpater(this,
            object : HostViewHolder.onClickListener {
                override fun onClickItem(position: Int) {
                    ChangeStatus(datas[position].txt_HostName, datas[position].txt_MAC, datas[position].txt_IP).start()
                    Toast.makeText(this@HostActivity, datas[position].toString() , Toast.LENGTH_SHORT).show()
                }
            }
        )

        rv_host.adapter = hostAdpater
        rv_host.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        Connect().start()
        if (!data.isNullOrEmpty() && cnt != 0) {
            loadDatas()
        }
    }

    //비동기 소켓통신
    class Connect() : Thread() {
        override fun run() {
            try {
                socket = Socket(ip, port)
                writeSocket = DataOutputStream(socket.getOutputStream())
                readSocket = DataInputStream(socket.getInputStream())

                msg = "list"
                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

//                cnt = readSocket.read()

                var dataArr = ByteArray(1024) // 1024만큼 데이터 받기
                readSocket.read(dataArr) // byte array에 데이터를 씁니다.
                data = String(dataArr)

                println("loadData : ${data}")

                cnt = data.split('/')[0].toInt()
                data = data.removePrefix(data.split('/')[0])

            } catch (e: Exception) {    //연결 실패
                socket.close()
            }

        }
    }

    class HostDisconnect : Thread() {
        override fun run() {
            try {
                socket.close()
                ThreadDeath()
            } catch (e: Exception) {

            }
        }
    }

    fun loadDatas() {

        println("loadData2 : ${data}")

        println("count : ${cnt}")

        var k = 0
        for (i in 0..cnt-1) {
            if (data.split('/')[i + 1] == "block") {
                k = 1
                datas.apply {
                    add(
                        HostData(
                            txt_HostName = data.split('/')[i + 2]!!.split(',')[0],
                            txt_MAC = data.split('/')[i + 2].split(',')[1],
                            txt_IP = data.split('/')[i + 2].split(',')[2],
                            status = true
                        )
                    )
                }
            } else if (k==0) {
                datas.apply {
                    add(
                        HostData(
                            txt_HostName = data.split('/')[i + 1]!!.split(',')[0],
                            txt_MAC = data.split('/')[i + 1].split(',')[1],
                            txt_IP = data.split('/')[i + 1].split(',')[2],
                            status = false
                        )
                    )
                }
            }
            else if(k == 1){
                datas.apply {
                    add(
                        HostData(
                            txt_HostName = data.split('/')[i + 2]!!.split(',')[0],
                            txt_MAC = data.split('/')[i + 2].split(',')[1],
                            txt_IP = data.split('/')[i + 2].split(',')[2],
                            status = true
                        )
                    )
                }
            }
        }

        hostAdpater.datas = datas
        hostAdpater.notifyDataSetChanged()

    }

    class ChangeStatus(host : String, mac : String, ip: String) : Thread() {
        val host = host
        val mac = mac
        val ip = ip
        override fun run() {
            try {
                socket = Socket(ip, port)
                writeSocket = DataOutputStream(socket.getOutputStream())
                readSocket = DataInputStream(socket.getInputStream())

                msg = "blockperson/${host}/${mac}/${ip}"
                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

            }catch (e: Exception){
                socket.close()
            }
        }
    }
}
