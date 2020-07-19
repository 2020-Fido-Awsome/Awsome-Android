package com.entersekt.fido2.fragment_log.active

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entersekt.fido2.R

class ActiveAdapter (private val context: Context): RecyclerView.Adapter<ActiveViewHolder>(){

    var datas = mutableListOf<ActiveData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_log_active, parent, false)
        return ActiveViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ActiveViewHolder, position: Int) {
        holder.bind(datas[position])
    }

}