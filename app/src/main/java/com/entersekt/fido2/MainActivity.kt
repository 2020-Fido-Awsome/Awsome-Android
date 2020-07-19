package com.entersekt.fido2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.entersekt.fido2.activity_admin.AdminActivity
import com.entersekt.fido2.activity_host.HostActivity
import kotlinx.android.synthetic.main.activity_information.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btn_Host.setOnClickListener {
            val intent = Intent(this, HostActivity::class.java)
            startActivity(intent)
        }
        btn_infromation.setOnClickListener {
            val intent = Intent(this, InformationActivity::class.java)
            startActivity(intent)
        }
        btn_security.setOnClickListener {
            val intent = Intent(this, SecurityActivity::class.java)
            startActivity(intent)
        }
        btn_log.setOnClickListener {
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
        }
        btn_reset.setOnClickListener {
            val intent = Intent(this, ResetActivity::class.java)
            startActivity(intent)
        }
        btn_admin.setOnClickListener {
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }
    }
    var lastTimeBackPressed = System.currentTimeMillis()
    override fun onBackPressed() {

        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            return
        }

        lastTimeBackPressed = System.currentTimeMillis()

        Toast.makeText(this,"버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show()
    }
}
