package com.entersekt.fido2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.entersekt.fido2.R
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import kotlinx.android.synthetic.main.activity_tutorial.*

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)
        tutorial_viewpager.adapter = TutorialPagerAdapter(supportFragmentManager)
        tutorial_viewpager.offscreenPageLimit = 2
        dots_indicator.setViewPager(tutorial_viewpager)


    }
}