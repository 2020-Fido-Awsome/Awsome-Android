package com.entersekt.fido2.fragment_log.active

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.entersekt.fido2.R
import com.entersekt.fido2.fragment_log.security.SecurityFragment
import kotlinx.android.synthetic.main.fragment_active.*
import kotlinx.coroutines.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket

class ActiveFragment : Fragment() {

    companion object{
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "172.20.10.2"  //서버 ip
        var port = 9999
        var msg = "0"
        var cnt = 0
        var data = ""
        val datas = mutableListOf<ActiveData>()
        lateinit var activeAdapter: ActiveAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_active, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activeAdapter = ActiveAdapter(view.context)
        println("activefragmentOnViewVreated")

        rv_active.adapter = activeAdapter
        rv_active.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
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

    fun connect(){
        socket = Socket(ip, port)
        writeSocket = DataOutputStream(socket.getOutputStream())
        readSocket = DataInputStream(socket.getInputStream())

        msg = "active_log"
        writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

        var dataArr = ByteArray(1024) // 1024만큼 데이터 받기
        readSocket.read(dataArr) // byte array에 데이터를 씁니다.
        data = String(dataArr)

        println("[active]data : $data")
        cnt = data.split("//")[1].toInt()
        println("[active]cnt : $cnt")


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

    private suspend fun loadDatas(){

        var dataArr = arrayOfNulls<String>(cnt)

        println("[active] loadDatas 수행")
        println("count : $cnt")
        println("loadData : $data")

        for(i in 1..cnt){
            dataArr[i-1] = data.split("//")[i+1]
        }

        for( i in 0 until cnt){
            println(dataArr[i])

            datas.apply {
                add(
                    ActiveData(
                        txt_ActiveDate = dataArr[i]!!.split(',')[0],
                        txt_ActiveTime = dataArr[i]!!.split(',')[1],
                        txt_ActiveContent = dataArr[i]!!.split(',')[2]
                    )
                )
            }

            activeAdapter.datas = datas
            activeAdapter.notifyDataSetChanged()

        }

    }

//    override fun onStart() {
//        super.onStart()
//        datas.clear()
//
//        println("activefragmentOnStart")
//        Connect().start()
//        loadDatas()
//    }

}
