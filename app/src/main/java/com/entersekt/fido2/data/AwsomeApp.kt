package com.entersekt.fido2.data

import android.app.Application

class AwsomeApp :Application(){
    companion object {
        lateinit var prefs : AwsomeSharedPreferences
    }

    override fun onCreate() {
        prefs = AwsomeSharedPreferences(applicationContext)
        super.onCreate()

        DataManage.init(this)
    }
}