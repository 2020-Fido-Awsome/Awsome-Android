package com.entersekt.fido2.fragment_log.security

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.entersekt.fido2.R
import com.entersekt.fido2.appdata.DataManage
import kotlinx.android.synthetic.main.fragment_security.*
import kotlinx.coroutines.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket


class SecurityFragment : Fragment() {

    companion object{
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "192.168.0.254"  //서버 ip
        var port = 9999
        var msg = "0"
        var cnt = 0
        var data = ""
        val datas = mutableListOf<SecurityData>()
        lateinit var securityAdapter: SecurityAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_security, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("securityfragmentOnViewCreated")

        securityAdapter = SecurityAdapter(view.context)
        rv_security.adapter = securityAdapter
        rv_security.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        Connect().start()
//        loadDatas()
//        onStart()

        viewLifecycleOwner.lifecycleScope.launch {
            val connect = withContext(Dispatchers.IO) {
                connect()
            }

            withContext(Dispatchers.Main) {
                loadDatas()
            }

        }
    }

    suspend fun connect(){
        socket = Socket(ip, port)
        writeSocket = DataOutputStream(socket.getOutputStream())
        readSocket = DataInputStream(socket.getInputStream())

        msg = "${DataManage.macAddress}/security_log"
        writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

        var dataArr = ByteArray(1024) // 1024만큼 데이터 받기
        readSocket.read(dataArr) // byte array에 데이터를 씁니다.
        data = String(dataArr)

        println("[security] data: $data")
        val status = data.split("//")[1]
        data = data.removePrefix(status)
        println("[security]prefix한 데이터 : $data")
        println("[security]prefix : $status")
        cnt = data.split("//")[2].toInt()
        println("[security]cnt : $cnt")

//        loadDatas()
    }


    class Disconnect:Thread(){
        override fun run() {
            try{
                socket.close()
//                ThreadDeath()
            }catch(e:Exception){

            }
        }
    }

    suspend fun loadDatas(){

        var dataArr = arrayOfNulls<String>(cnt)

        println("count : $cnt")
        println("loadData : $data")

        for(i in 1..cnt){
            dataArr[i-1] = data.split("//")[i+2]
        }

        for( i in 0 until cnt){
            println(dataArr[i])

            datas.apply {
                add(
                    SecurityData(
                        txt_SecurityDate = dataArr[i]!!.split(',')[0],
                        txt_SecurityTime = dataArr[i]!!.split(',')[1],
                        txt_SecurityContent = dataArr[i]!!.split(',')[2]
                    )
                )
            }

            securityAdapter.datas = datas
            securityAdapter.notifyDataSetChanged()

        }
    }

//    override fun onStart() {
//        super.onStart()
//        datas.clear()
//
//        println("securityFragmentOnStart")
////        Connect().start()
////        loadDatas()
//    }
}
