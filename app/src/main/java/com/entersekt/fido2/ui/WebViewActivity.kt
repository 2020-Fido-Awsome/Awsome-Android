package com.entersekt.fido2.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.webkit.WebView
import com.entersekt.fido2.R

class WebViewActivity : AppCompatActivity() {

    private val CLOSE_TIME: Long = 10000 //10초
    val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url = "https://aws.eazysecure-auth.com"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)

        handler.postDelayed(runnableCode, 3000)

//        finish()

//        Handler().postDelayed({ //delay를 위한 handler
//            finish()
//        }, CLOSE_TIME)


    }

    val runnableCode =
        Runnable {
//            finish()
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }

}