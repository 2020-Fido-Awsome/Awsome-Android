package com.entersekt.fido2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_host.btn_back
import kotlinx.android.synthetic.main.activity_revise.*

class ReviseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revise)

//        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        btn_back.setOnClickListener {
            finish()
        }

        btn_revise.setOnClickListener {
            if(txt_newUserName.text.isNullOrBlank() || txt_pwd.text.isNullOrBlank() || txt_repwd.text.isNullOrBlank()){
                Toast.makeText(this, "아이디 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                if(txt_pwd.text.toString().equals(txt_repwd.text.toString())){
//                    inputMethodManager.hideSoftInputFromWindow(txt_repwd.getWindowToken(), 0);

//                    txt_userName.setText(txt_newUserName.text)
//                    Toast.makeText(this, "변경 완료", Toast.LENGTH_SHORT).show()
//                    txt_newUserName.setText("")
//                    txt_pwd.setText("")
//                    txt_repwd.setText("")

                    val intent = Intent(this, InformationActivity::class.java)
                    intent.putExtra("username", txt_newUserName.text.toString())
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
