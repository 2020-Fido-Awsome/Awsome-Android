package com.entersekt.fido2.activity_host

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entersekt.fido2.R

class HostViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

    val txt_HostName = itemView.findViewById<TextView>(R.id.txt_HostName)
    val txt_MAC = itemView.findViewById<TextView>(R.id.txt_MAC)
    val txt_IP = itemView.findViewById<TextView>(R.id.txt_IP)

    fun bind(hostData: HostData){
        txt_HostName.text = hostData.txt_HostName
        txt_MAC.text = hostData.txt_MAC
        txt_IP.text = hostData.txt_IP
    }
}