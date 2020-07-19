package com.entersekt.fido2.activity_admin

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entersekt.fido2.R
import com.entersekt.fido2.activity_admin.AdminViewHolder
import com.entersekt.fido2.activity_admin.AdminData


class AdminAdapter (private val context: Context): RecyclerView.Adapter<AdminViewHolder>(){
    var datas  = mutableListOf<AdminData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_admin, parent, false)
        return AdminViewHolder(view)
    }

    override fun getItemCount(): Int {return datas.size}

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {holder.bind(datas[position])}

}