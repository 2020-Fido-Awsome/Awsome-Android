package com.entersekt.fido2.ui

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.entersekt.fido2.R
import com.entersekt.fido2.fragment_log.LogAdapter
import com.entersekt.fido2.fragment_log.active.ActiveFragment
import com.entersekt.fido2.fragment_log.security.SecurityFragment
import kotlinx.android.synthetic.main.activity_log.*


class LogActivity : AppCompatActivity() {


    private val activeFragment : Fragment = ActiveFragment()
    private val securityFragment = SecurityFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        btn_back.setOnClickListener {
//            ActiveFragment.Disconnect().start()
            finish()
        }

        viewpager.adapter = LogAdapter(supportFragmentManager)
        viewpager.offscreenPageLimit=2

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) { //0, 1
                Toast.makeText(this@LogActivity, position.toString(), Toast.LENGTH_SHORT).show()
//
//                if(position == 1){
//                    activeFragment.onStart()
//                }else{
//                    securityFragment.onStart()
//                }

            }
        })

        btn_switch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewpager.currentItem=1
                txt_securitylog.visibility = View.VISIBLE
                txt_activelog.visibility = View.INVISIBLE
            } else {
                viewpager.currentItem=0
                txt_securitylog.visibility = View.INVISIBLE
                txt_activelog.visibility = View.VISIBLE
            }
        })
    }
}
