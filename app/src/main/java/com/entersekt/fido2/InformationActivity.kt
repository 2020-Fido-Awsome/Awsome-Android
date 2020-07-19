package com.entersekt.fido2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_information.*
import kotlinx.android.synthetic.main.activity_revise.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_revise.btn_back

class InformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        btn_editpw.setOnClickListener {
            val intent = Intent(this, ReviseActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_back.setOnClickListener {
            finish()
        }

        if(intent.hasExtra("username")){
            txt_User2.text = intent.getStringExtra("username")
        }
    }
}
