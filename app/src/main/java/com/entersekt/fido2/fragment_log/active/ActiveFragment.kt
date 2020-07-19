package com.entersekt.fido2.fragment_log.active

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.entersekt.fido2.R
import kotlinx.android.synthetic.main.fragment_active.*

class ActiveFragment : Fragment() {

    lateinit var activeAdapter: ActiveAdapter
    val datas = mutableListOf<ActiveData>()

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
        rv_active.adapter = activeAdapter
        rv_active.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        loadDatas()
    }

    fun loadDatas(){
        datas.apply {
            add(
                ActiveData(
                    txt_ActiveDate = "2020/11/11",
                    txt_ActiveTime = "12:12:12",
                    txt_ActiveContent = "길동님이 ARP 스푸핑 기능을 켰습니다."
                )
            )
            add(
                ActiveData(
                    txt_ActiveDate = "2020/11/11",
                    txt_ActiveTime = "12:12:12",
                    txt_ActiveContent = "길동님이 ARP 스푸핑 기능을 껐습니다."
                )
            )
            add(
                ActiveData(
                    txt_ActiveDate = "2020/03/11",
                    txt_ActiveTime = "11:23:14",
                    txt_ActiveContent = "영희님이 해외 ip 차단 기능을 켰습니다."
                )

            )
        }
        activeAdapter.datas = datas
        activeAdapter.notifyDataSetChanged()
    }

}
