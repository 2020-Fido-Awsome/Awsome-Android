package com.entersekt.fido2.ui.activity_tutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.entersekt.fido2.R
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        tutorial_viewpager.adapter =
            TutorialPagerAdapter(
                supportFragmentManager
            )
        tutorial_viewpager.offscreenPageLimit = 4
        dots_indicator.setViewPager(tutorial_viewpager)

    }
}