package com.entersekt.fido2.fragment_log.security

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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