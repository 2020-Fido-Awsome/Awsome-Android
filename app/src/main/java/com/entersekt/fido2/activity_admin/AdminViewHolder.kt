package com.entersekt.fido2.activity_admin

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entersekt.fido2.R
import kotlinx.android.synthetic.main.activity_main.*

class AdminViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

    val txt_AdminUser = itemView.findViewById<TextView>(R.id.txt_AdminUser)
    val txt_AdminRating = itemView.findViewById<TextView>(R.id.txt_AdminRating)

    fun bind(adminData: AdminData){
        txt_AdminUser.text = adminData.txt_AdminUser
        txt_AdminRating.text = adminData.txt_AdminRating
    }
}