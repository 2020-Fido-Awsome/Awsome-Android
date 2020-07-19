package com.entersekt.fido2.fragment_log.active

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entersekt.fido2.R

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