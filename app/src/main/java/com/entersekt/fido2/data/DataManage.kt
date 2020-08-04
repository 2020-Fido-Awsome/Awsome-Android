package com.entersekt.fido2.data

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.putString
import com.entersekt.fido2.data.AwsomeApp.Companion.prefs

object DataManage{
    private lateinit var pref: SharedPreferences
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
}