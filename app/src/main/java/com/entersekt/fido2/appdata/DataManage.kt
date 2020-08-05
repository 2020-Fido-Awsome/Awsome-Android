package com.entersekt.fido2.appdata

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.entersekt.fido2.appdata.AwsomeApp
import java.util.*


object DataManage{
    ///private lateinit var pref: SharedPreferences
    lateinit var pref: SharedPreferences
    fun init(context: Context) {
        pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
    }

    var macAddress: String?
        get() = pref.getString("MAC", "00:00:00:00:00:00")
        set(value) = pref.edit{
            it.putString("MAC", value)?.apply()
        }!!

    /* get/set 함수 임의 설정. get 실행 시 저장된 값을 반환하며 default 값은 ""
     * set(value) 실행 시 value로 값을 대체한 후 저장 */


    private inline fun SharedPreferences.edit(
        operation:
            (SharedPreferences.Editor) -> Unit
    ) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }



// ssid, pw값
    var ws: String?
        get() = pref.getString("ws", "AWS")
        set(value) = pref.edit{
            it.putString("ws", value)?.apply()
        }

    var iniPw = AwsomeApp.encryptData("awsfido2020!")
    @RequiresApi(Build.VERSION_CODES.O)
    var iniPw1 = Base64.getEncoder().encodeToString(iniPw.first)
    @RequiresApi(Build.VERSION_CODES.O)
    var iniPw2 = Base64.getEncoder().encodeToString(iniPw.second)

    var wp1: String?
        get() = pref.getString("wp1", iniPw1)
        set(value) = pref.edit{
            it.putString("wp1", value)?.apply()
        }

    var wp2: String?
        get() = pref.getString("wp2", iniPw2)
        set(value) = pref.edit {
            it.putString("wp2", value)?.apply()
        }
}