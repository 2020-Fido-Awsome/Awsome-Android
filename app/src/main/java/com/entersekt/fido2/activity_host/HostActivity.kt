package com.entersekt.fido2.activity_host

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.entersekt.fido2.R
import kotlinx.android.synthetic.main.activity_host.*

class HostActivity : AppCompatActivity() {

    lateinit var hostAdpater: HostAdpater
    val datas = mutableListOf<HostData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        btn_back.setOnClickListener {
            finish()
        }

        hostAdpater = HostAdpater(this)
        rv_host.adapter = hostAdpater
        rv_host.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
        loadDatas()
    }

    fun loadDatas(){
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
}
