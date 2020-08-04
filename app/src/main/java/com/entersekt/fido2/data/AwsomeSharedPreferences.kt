package com.entersekt.fido2.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AwsomeSharedPreferences (context: Context){

    val prefs: SharedPreferences? = context.getSharedPreferences("prefs", 0)
    /* 파일 이름과 EditText를 저장할 Key 값을 만들고 prefs 인스턴스 초기화 */


}
