package com.entersekt.fido2.ui

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.entersekt.fido2.R
import com.entersekt.fido2.fragment_log.LogAdapter
import kotlinx.android.synthetic.main.activity_log.*


class LogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        btn_back.setOnClickListener {
//            ActiveFragment.Disconnect().start()
            finish()
        }

        viewpager.adapter = LogAdapter(supportFragmentManager)
        viewpager.offscreenPageLimit = 2

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) { //0, 1
            }
        })

        btn_switch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewpager.currentItem = 1
                txt_securitylog.visibility = View.VISIBLE
                txt_activelog.visibility = View.INVISIBLE
            } else {
                viewpager.currentItem = 0
                txt_securitylog.visibility = View.INVISIBLE
                txt_activelog.visibility = View.VISIBLE
            }
        })
    }
}
