package com.entersekt.fido2.activity_admin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entersekt.fido2.R
import com.entersekt.fido2.activity_host.HostViewHolder
import kotlinx.android.synthetic.main.item_admin.view.*
import kotlinx.android.synthetic.main.item_host.view.*

class AdminAdapter(
    private val context: Context,
    private val clickListener: AdminViewHolder.onClickListener
) : RecyclerView.Adapter<AdminViewHolder>() {
    var datas = mutableListOf<AdminData>()
    var previousPostition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_admin, parent, false)
        return AdminViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        holder.bind(datas[position])
        previousPostition = position
    }

}

class AdminViewHolder(
    itemView: View,
    val clickListener: onClickListener
) : RecyclerView.ViewHolder(itemView) {

    val txt_AdminUser = itemView.findViewById<TextView>(R.id.txt_AdminUser)
    val txt_AdminRating = itemView.findViewById<TextView>(R.id.txt_AdminRating)

    fun bind(adminData: AdminData) {
        txt_AdminUser.text = adminData.txt_AdminUser
        txt_AdminRating.text = adminData.txt_AdminRating
    }

    init {
        itemView.btn_delete.setOnClickListener {
            clickListener.onClickItem(adapterPosition)
        }
    }

    interface onClickListener {
        fun onClickItem(position: Int)
    }
}