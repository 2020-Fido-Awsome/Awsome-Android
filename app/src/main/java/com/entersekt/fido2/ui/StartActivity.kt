package com.entersekt.fido2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign.*

class StartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        btn_SignUp.setOnClickListener { //회원가입
            val intent = Intent(this, CreateActivity::class.java)

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent)
       }

        btn_SignIn.setOnClickListener { //로그인
            val intent = Intent(this, AuthnActivity::class.java)

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent)
            finish()
        }
    }

    var lastTimeBackPressed:Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed >= 2000) {
            lastTimeBackPressed = System.currentTimeMillis()
            Toast.makeText(this,"버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }
}