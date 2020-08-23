package com.entersekt.fido2.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.entersekt.fido2.fragment_tutorial.FirstFragment
import com.entersekt.fido2.fragment_tutorial.SecondFragment
import com.entersekt.fido2.fragment_tutorial.ThirdFragment

class TutorialPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> FirstFragment()
            1 -> SecondFragment()
            else -> ThirdFragment()
        }
    }

    override fun getCount() =  3
}