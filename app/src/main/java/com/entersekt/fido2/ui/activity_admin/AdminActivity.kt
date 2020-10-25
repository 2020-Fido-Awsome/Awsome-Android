package com.entersekt.fido2.activity_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.entersekt.fido2.R
import com.entersekt.fido2.appdata.DataManage
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import kotlinx.android.synthetic.main.activity_admin.btn_back

class AdminActivity : AppCompatActivity() {

    companion object {
        var socket = Socket()
        lateinit var writeSocket: DataOutputStream
        lateinit var readSocket: DataInputStream
        var ip = "192.168.0.254"  //서버 ip
        var port = 9999
        var msg = "0"
        var cnt = 0
        var data = ""
        val datas = mutableListOf<AdminData>()
        lateinit var adminAdapter: AdminAdapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        datas.clear()

        btn_back.setOnClickListener {
            finish()
        }

        fab.setOnClickListener {
            val intent = Intent(this, CertifyActivity::class.java)
            startActivity(intent)
        }

        adminAdapter = AdminAdapter(this,
            object : AdminViewHolder.onClickListener {
                override fun onClickItem(position: Int) {
                    ChangeStatus(
                        position
                    )
                    println("다시 재정비")
                    adminAdapter.notifyDataSetChanged()
                }
            })

        rv_admin.adapter = adminAdapter
        rv_admin.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        GlobalScope.launch(Dispatchers.Main) {

            async(Dispatchers.IO) {
                first()
            }.await()

            async(Dispatchers.Default) {
                second()
            }.await()

            adminAdapter.notifyDataSetChanged()
        }

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rv_admin)

    }

    fun ChangeStatus(position: Int) {
//데이터 삭제
        if (position == 0) {
            Toast.makeText(this, "최고 관리자는 삭제할 수 없습니다.", Toast.LENGTH_LONG).show()
        } else {
            datas.removeAt(position)
            adminAdapter.notifyDataSetChanged()
            Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun first() {
        println("첫번째 함수 ")
        socket = Socket(ip, port)
        writeSocket = DataOutputStream(socket.getOutputStream())
        readSocket = DataInputStream(socket.getInputStream())

        msg = "${DataManage.macAddress}/adminlist"
        writeSocket.write(msg.toByteArray())    //메시지 전송 명령 전송

        var dataArr = ByteArray(1024) // 1024만큼 데이터 받기
        readSocket.read(dataArr) // byte array에 데이터를 씁니다.
        data = String(dataArr)
        cnt = data.split('/')[0].toInt()
        println("loadData : ${data} ${cnt}")
        println("첫번째 함수 끝")
    }

    fun second() {
        println("두번째 함수 실행")
        for (i in 1..cnt) {
            datas.apply {
                add(
                    AdminData(
                        txt_AdminUser = data.split('/')[i].split(',')[0],
                        txt_AdminRating = data.split('/')[i].split(',')[1]
                    )
                )
            }

        }
        adminAdapter.datas = datas
        println("두번째 함수 끝")
    }
}
