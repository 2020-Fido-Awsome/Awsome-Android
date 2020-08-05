package com.entersekt.fido2.appdata

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.entersekt.fido2.appdata.AwsomeApp

class AwsomeSharedPreferences (context: Context){

    val prefs: SharedPreferences? = context.getSharedPreferences("prefs", 0)
    /* 파일 이름과 EditText를 저장할 Key 값을 만들고 prefs 인스턴스 초기화 */

    @RequiresApi(Build.VERSION_CODES.M)
    public fun checkFirstRun() { // 첫 실행시 키 생성
        var isFirstRun: Boolean = prefs!!.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            AwsomeApp.genKey()
            println("!!!!!!!!!!generated key!!!!!!!!!!!!!!!!!!!")
            this.prefs.edit().putBoolean("isFirstRun", false).apply()
        }
    }

}
