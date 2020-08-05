package com.entersekt.fido2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.entersekt.fido2.R
import com.entersekt.fido2.activity_host.HostActivity
import com.entersekt.fido2.appdata.DataManage
import kotlinx.android.synthetic.main.activity_security.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket


class SecurityActivity : AppCompatActivity() {

    companion object {
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "172.20.10.2"  //서버 ip
        var port = 9999
        var msg = "0"
        var data = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security)

//        StartConnect().start()

        btn_back.setOnClickListener {
//            SecurityDisconnect().start()
            finish()
        }

        GlobalScope.launch(Dispatchers.Main) {
            async(Dispatchers.IO) {

                async {
                    StartConnect()
                }.await()


//            if (!data.isNullOrEmpty()) {
//                setStatus()
//            }
            }

            GlobalScope.launch {

                act_sec_switch_arp_spoofing.setOnClickListener {
                    async(Dispatchers.IO) {
                        if (act_sec_switch_arp_spoofing.isChecked) {
//                        Connect(1).start()
                            connect(1)
                        } else connect(0)
                    }
                }


                act_sec_switch_syn_flooding.setOnClickListener {
                    async(Dispatchers.IO) {
                        if (act_sec_switch_syn_flooding.isChecked) {
                            connect(3)
                        } else connect(2)
                    }
                }

                act_sec_switch_ddos.setOnClickListener {
                    async(Dispatchers.IO) {
                        if (act_sec_switch_ddos.isChecked) {
                            connect(5)
                        } else connect(4)
                    }
                }

                act_sec_switch_dns_spoofing.setOnClickListener {
                    async(Dispatchers.IO) {
                        if (act_sec_switch_dns_spoofing.isChecked) {
                            connect(7)
                        } else connect(6)
                    }
                }

                act_sec_switch_command_injection.setOnClickListener {
                    async(Dispatchers.IO) {
                        if (act_sec_switch_command_injection.isChecked) {
                            connect(9)
                        } else connect(8)
                    }
                }

                act_sec_switch_qr.setOnClickListener {
                    async(Dispatchers.IO) {
                        if (act_sec_switch_qr.isChecked) {
                            connect(11)
                        } else connect(10)
                    }
                }
            }

//            val connect = async(Dispatchers.IO) {
//                connect()
//            }.await()
//
//            async(Dispatchers.Default) {
//                second()
//            }.await()
        }
    }


    fun StartConnect() {

        socket = Socket(ip, port)
        writeSocket = DataOutputStream(socket.getOutputStream())
        readSocket = DataInputStream(socket.getInputStream())

        msg = "status"
        writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

        var dataArr = ByteArray(1024) // 1024만큼 데이터 받기
        readSocket.read(dataArr) // byte array에 데이터를 씁니다.
        data = String(dataArr)// 서버에서 보내준 한 줄 전체 - 쓰레기값 지움
        println("data : $data")
        setStatus()

    }


//    class StartConnect : Thread() {
//        override fun run() {
//            try {
//                socket = Socket(ip, port)
//                writeSocket = DataOutputStream(socket.getOutputStream())
//                readSocket = DataInputStream(socket.getInputStream())
//
//                msg = "status"
//                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송
//
//                var dataArr = ByteArray(1024) // 1024만큼 데이터 받기
//                readSocket.read(dataArr) // byte array에 데이터를 씁니다.
//                data = String(dataArr)// 서버에서 보내준 한 줄 전체 - 쓰레기값 지움
//                println("data : $data")
//
//            } catch (e: Exception) {    //연결 실패
//                socket.close()
//            }
//        }
//    }

    class Connect(i: Int) : Thread() {
        private val con = i
        override fun run() {
            try {
//                socket = Socket(ip, port)
//                writeSocket = DataOutputStream(socket.getOutputStream())
//                readSocket = DataInputStream(socket.getInputStream())
//
//                when (con) {
//                    0 -> msg = "${DataManage.macAddress}/offarp"
//                    1 -> msg = "${DataManage.macAddress}/onarp"
//                    2 -> msg = "${DataManage.macAddress}/offsyn"
//                    3 -> msg = "${DataManage.macAddress}/onsyn"
//                    4 -> msg = "${DataManage.macAddress}/offdos"
//                    5 -> msg = "${DataManage.macAddress}/ondos"
//                    6 -> msg = "${DataManage.macAddress}/offdns"
//                    7 -> msg = "${DataManage.macAddress}/ondns"
//                    8 -> msg = "${DataManage.macAddress}/offcmd"
//                    9 -> msg = "${DataManage.macAddress}/oncmd"
//                    10 -> msg = "${DataManage.macAddress}/offqr"
//                    11 -> msg = "${DataManage.macAddress}/onqr"
//                }
//
//                writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

            } catch (e: Exception) {    //연결 실패
                socket.close()
            }
        }
    }

//    class SecurityDisconnect : Thread() {
//        override fun run() {
//            try {
//                socket.close()
//                ThreadDeath()
//            } catch (e: Exception) {
//
//            }
//        }
//    }

    private fun setStatus() {
        println("data!!!! : $data")

        act_sec_switch_arp_spoofing.isChecked = (data.split(',')[1] == "on")
        act_sec_switch_syn_flooding.isChecked = (data.split(',')[2] == "on")
        act_sec_switch_ddos.isChecked = data.split(',')[3] == "on"
        act_sec_switch_dns_spoofing.isChecked = data.split(',')[4] == "on"
        act_sec_switch_command_injection.isChecked = data.split(',')[5] == "on"
        act_sec_switch_qr.isChecked = data.split(',')[6] == "on"

    }


    fun connect(con: Int) {
        socket = Socket(ip, port)
        writeSocket = DataOutputStream(socket.getOutputStream())
        readSocket = DataInputStream(socket.getInputStream())

        when (con) {
            0 -> msg = "${DataManage.macAddress}/offarp"
            1 -> msg = "${DataManage.macAddress}/onarp"
            2 -> msg = "${DataManage.macAddress}/offsyn"
            3 -> msg = "${DataManage.macAddress}/onsyn"
            4 -> msg = "${DataManage.macAddress}/offdos"
            5 -> msg = "${DataManage.macAddress}/ondos"
            6 -> msg = "${DataManage.macAddress}/offdns"
            7 -> msg = "${DataManage.macAddress}/ondns"
            8 -> msg = "${DataManage.macAddress}/offcmd"
            9 -> msg = "${DataManage.macAddress}/oncmd"
            10 -> msg = "${DataManage.macAddress}/offqr"
            11 -> msg = "${DataManage.macAddress}/onqr"
        }

        writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

    }

}