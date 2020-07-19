package com.entersekt.fido2.fragment_log.security

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entersekt.fido2.R

class SecurityViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

    val txt_SecurityDate = itemView.findViewById<TextView>(R.id.txt_SecurityDate)
    val txt_SecurityTime = itemView.findViewById<TextView>(R.id.txt_SecurityTime)
    val txt_SecurityContent = itemView.findViewById<TextView>(R.id.txt_SecurityContent)

    fun bind(securityData: SecurityData){
        txt_SecurityDate.text =securityData.txt_SecurityDate
        txt_SecurityTime.text = securityData.txt_SecurityTime
        txt_SecurityContent.text = securityData.txt_SecurityContent
    }
}