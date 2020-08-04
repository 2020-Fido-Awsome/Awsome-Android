package com.entersekt.fido2.activity_admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.entersekt.fido2.R
import com.entersekt.fido2.activity_admin.AdminAdapter
import com.entersekt.fido2.activity_admin.AdminData
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity() {

    lateinit var adminAdapter: AdminAdapter
    val datas = mutableListOf<AdminData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        btn_back.setOnClickListener {
            finish()
        }

        adminAdapter = AdminAdapter(this)
        rv_admin.adapter = adminAdapter
        rv_admin.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        loadDatas()

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rv_admin)

    }

    fun loadDatas(){
        datas.apply {
            add(
                AdminData(
                    txt_AdminUser = "우리집 공주 길동",
                    txt_AdminRating = "최고관리자"
                )
            )
            add(
                AdminData(
                    txt_AdminUser = "길동이 아빠",
                    txt_AdminRating = "관리자"
                )
            )
            add(
                AdminData(
                    txt_AdminUser = "길동이 엄마",
                    txt_AdminRating = "관리자"
                )
            )
            add(
                AdminData(
                    txt_AdminUser = "길동이 동생",
                    txt_AdminRating = "관리자"
                )
            )
            add(
                AdminData(
                    txt_AdminUser = "길동이 절친",
                    txt_AdminRating = "관리자"
                )
            )
        }
        adminAdapter.datas = datas
        adminAdapter.notifyDataSetChanged()
    }
}
