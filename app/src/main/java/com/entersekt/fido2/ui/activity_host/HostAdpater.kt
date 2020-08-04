package com.entersekt.fido2.activity_host

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entersekt.fido2.R
import kotlinx.android.synthetic.main.item_host.view.*

class HostAdpater(
    private val context: Context,
    private val clickListener: HostViewHolder.onClickListener
) : RecyclerView.Adapter<HostViewHolder>() {
    var datas = mutableListOf<HostData>()
    var previousPostition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HostViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_host, parent, false)
        return HostViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: HostViewHolder, position: Int) {
        holder.bind(datas[position])
        previousPostition = position
    }
}

class HostViewHolder(
    itemView: View,
    val clickListener: onClickListener
) : RecyclerView.ViewHolder(itemView) {

    val txt_HostName = itemView.findViewById<TextView>(R.id.txt_HostName)
    val txt_MAC = itemView.findViewById<TextView>(R.id.txt_MAC)
    val txt_IP = itemView.findViewById<TextView>(R.id.txt_IP)
    val background = itemView.findViewById<LinearLayout>(R.id.background)

    fun bind(hostData: HostData) {
        txt_HostName.text = hostData.txt_HostName
        txt_MAC.text = hostData.txt_MAC
        txt_IP.text = hostData.txt_IP

        if (hostData.status) {
            background.setBackgroundResource(R.color.grey)
            txt_HostName.paintFlags = txt_HostName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            txt_MAC.paintFlags = txt_MAC.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            txt_IP.paintFlags = txt_IP.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            itemView.btn_host_item.setBackgroundResource(R.drawable.revival_cut)
        } else {
            background.setBackgroundResource(R.color.white)
            itemView.btn_host_item.setBackgroundResource(R.drawable.delete_cut)
        }

    }

    init {
        itemView.btn_host_item.setOnClickListener {
            clickListener.onClickItem(adapterPosition)
        }
    }

    interface onClickListener {
        fun onClickItem(position: Int)
    }
}