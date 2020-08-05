package com.entersekt.fido2.activity_host

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.entersekt.fido2.R
import com.entersekt.fido2.appdata.DataManage
import kotlinx.android.synthetic.main.activity_host.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket


class HostActivity : AppCompatActivity() {

    companion object {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        datas.clear()

        println(DataManage.macAddress)

        btn_back.setOnClickListener {
//            HostDisconnect().start()
            finish()
        }

        hostAdpater = HostAdpater(this,
            object : HostViewHolder.onClickListener {
                override fun onClickItem(position: Int) {
                    ChangeStatus(
                        position,
                        datas[position].txt_HostName,
                        datas[position].txt_MAC,
                        datas[position].txt_IP,
                        datas[position].status
                    )

                    println("다시 재정비")
                    hostAdpater.notifyDataSetChanged()

                }
            }
        )

        rv_host.adapter = hostAdpater
        rv_host.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))

        GlobalScope.launch(Dispatchers.Main) {

            async(Dispatchers.IO) {
                first()
            }.await()

            async(Dispatchers.Default) {
                second()
            }.await()

            hostAdpater.notifyDataSetChanged()
        }

//        Connect().start()
//        ObserveChange().start()
//        if (!data.isNullOrEmpty() && cnt != 0) {
//            loadDatas()
//        }

    }


    fun ChangeStatus(position: Int, host: String, mac: String, ip: String, status: Boolean){

        GlobalScope.launch(Dispatchers.IO){
            val sendHost = host
            val sendMac = mac
            val sendIp = ip
            val status = status

            Log.e("아이템클릭", "$position, $sendHost, $sendMac, $sendIp, $status")

            socket = Socket(Companion.ip, port)
            writeSocket = DataOutputStream(socket.getOutputStream())
            readSocket = DataInputStream(socket.getInputStream())

            println("datasize : ${datas.size}")

            if(!status){
                msg = "${DataManage.macAddress}/blockperson/${host}/${Companion.ip}/${mac}"
                datas[position].status = !datas[position].status
            }else {
                msg = "${DataManage.macAddress}/allowperson/${host}/${Companion.ip}/${mac}"
                datas[position].status = !datas[position].status
            }

            writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

//            changeData(position, host, mac, ip, status)

        }

    }

    fun first() {
        println("첫번째 함수 실행")
        socket = Socket(ip, port)
        writeSocket = DataOutputStream(socket.getOutputStream())
        readSocket = DataInputStream(socket.getInputStream())

        msg = "list"
        writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

        var dataArr = ByteArray(1024) // 1024만큼 데이터 받기
        readSocket.read(dataArr) // byte array에 데이터를 씁니다.
        data = String(dataArr)
//        cnt = data.split('/')[0].toInt()
        cnt = 3
        println("loadData : $data $cnt")
        println("첫번째 함수 끝")

    }

    fun second() {
        println("두번째 함수 실행")

        var k = 0
        for (i in 0 until cnt) {
            if (data.split('/')[i + 1] == "block") {
                k = 1
                datas.apply {
                    add(
                        HostData(
                            txt_HostName = data.split('/')[i + 2].split(',')[0],
                            txt_MAC = data.split('/')[i + 2].split(',')[1],
                            txt_IP = data.split('/')[i + 2].split(',')[2],
                            status = true
                        )
                    )
                }
            } else if (k == 0) {
                datas.apply {
                    add(
                        HostData(
                            txt_HostName = data.split('/')[i + 1].split(',')[0],
                            txt_MAC = data.split('/')[i + 1].split(',')[1],
                            txt_IP = data.split('/')[i + 1].split(',')[2],
                            status = false
                        )
                    )
                }
            } else if (k == 1) {
                datas.apply {
                    add(
                        HostData(
                            txt_HostName = data.split('/')[i + 2].split(',')[0],
                            txt_MAC = data.split('/')[i + 2].split(',')[1],
                            txt_IP = data.split('/')[i + 2].split(',')[2],
                            status = true
                        )
                    )
                }
            }
        }
        hostAdpater.datas = datas
        println("두번째 함수 끝")
    }

}