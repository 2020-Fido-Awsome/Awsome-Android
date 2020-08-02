package com.entersekt.fido2.fragment_log.active

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

class ActiveViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

    val txt_ActiveDate = itemView.findViewById<TextView>(R.id.txt_ActiveDate)
    val txt_ActiveTime = itemView.findViewById<TextView>(R.id.txt_ActiveTime)
    val txt_ActiveContent = itemView.findViewById<TextView>(R.id.txt_ActiveContent)

    fun bind(activeData: ActiveData){
        txt_ActiveDate.text = activeData.txt_ActiveDate
        txt_ActiveTime.text = activeData.txt_ActiveTime
        txt_ActiveContent.text = activeData.txt_ActiveContent
    }
}