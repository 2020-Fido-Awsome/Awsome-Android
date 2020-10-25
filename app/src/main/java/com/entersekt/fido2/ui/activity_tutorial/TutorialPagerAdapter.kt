package com.entersekt.fido2.ui.activity_tutorial

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.entersekt.fido2.fragment_tutorial.*

class TutorialPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> FirstFragment()
            1 -> SecondFragment()
            2 -> ThirdFragment()
            3 -> FourthFragment()
            else -> FifthFragment()
        }
    }

    override fun getCount() =  5
}