package com.entersekt.fido2.fragment_log.security

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.entersekt.fido2.R
import kotlinx.android.synthetic.main.fragment_security.*


class SecurityFragment : Fragment() {

    lateinit var securityAdapter: SecurityAdapter
    val datas = mutableListOf<SecurityData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_security, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        securityAdapter = SecurityAdapter(view.context)
        rv_security.adapter = securityAdapter
        rv_security.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        loadDatas()
    }

    private fun loadDatas() {
        datas.apply {
            add(
                SecurityData(
                    txt_SecurityDate = "2020/11/11",
                    txt_SecurityTime = "12:12:12",
                    txt_SecurityContent = "ARP 스푸핑 탐지"
                )
            )
            add(
                SecurityData(
                    txt_SecurityDate = "2020/03/11",
                    txt_SecurityTime = "11:23:14",
                    txt_SecurityContent = "해외 ip 접근 탐지"
                )
            )
        }
        securityAdapter.datas = datas
        securityAdapter.notifyDataSetChanged()
    }
}
