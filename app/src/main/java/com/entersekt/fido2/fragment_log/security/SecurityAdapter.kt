package com.entersekt.fido2.fragment_log.security

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entersekt.fido2.R

class SecurityAdapter(private val context: Context) : RecyclerView.Adapter<SecurityViewHolder>(){

    var datas = mutableListOf<SecurityData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecurityViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_log_security, parent, false)
        return SecurityViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: SecurityViewHolder, position: Int) {
        holder.bind(datas[position])
    }

}