package com.entersekt.fido2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_store.*

class StoreActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store)

//        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager



        btn_put.setOnClickListener {
            if(text_defaultUserName.text.isNullOrBlank() || txt_defaultpwd.text.isNullOrBlank() || txt_ckpwd.text.isNullOrBlank()){
                Toast.makeText(this, "닉네임과 비밀번호가 입력되었는지 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                if(txt_defaultpwd.text.toString().equals(txt_ckpwd.text.toString())){
//                    inputMethodManager.hideSoftInputFromWindow(txt_ckpwd.getWindowToken(), 0);

//                    txt_userName.setText(txt_newUserName.text)
//                    Toast.makeText(this, "변경 완료", Toast.LENGTH_SHORT).show()
//                    txt_newUserName.setText("")
//                    txt_pwd.setText("")
//                    txt_repwd.setText("")

                    val intent = Intent(this, AuthnActivity::class.java)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
