package com.entersekt.fido2.appdata

import android.app.Application

class AwsomeApp :Application(){
    companion object{

        lateinit var  globalApplication: AwsomeApp
        lateinit var instance : AwsomeApp

        fun getGlobalApplicationContext(): AwsomeApp {
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()

        instance = this
        globalApplication = this
        DataManage.init(this)
    }
}