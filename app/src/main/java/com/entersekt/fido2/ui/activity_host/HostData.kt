package com.entersekt.fido2.activity_host

data class HostData(
    val txt_HostName:String,
    val txt_MAC:String,
    val txt_IP:String,
    var status: Boolean
)