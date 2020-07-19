package com.entersekt.fido2.activity_host

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entersekt.fido2.R

class HostAdpater (private val context: Context): RecyclerView.Adapter<HostViewHolder>(){
    var datas = mutableListOf<HostData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HostViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_host, parent, false)
        return HostViewHolder(view)    }

    override fun getItemCount(): Int {return datas.size}

    override fun onBindViewHolder(holder: HostViewHolder, position: Int) {holder.bind(datas[position])}

}