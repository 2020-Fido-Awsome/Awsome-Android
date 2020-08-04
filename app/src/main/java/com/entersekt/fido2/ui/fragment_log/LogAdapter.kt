package com.entersekt.fido2.fragment_log

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.entersekt.fido2.fragment_log.active.ActiveFragment
import com.entersekt.fido2.fragment_log.security.SecurityFragment
import kotlinx.android.synthetic.main.activity_log.*

class LogAdapter(fm: FragmentManager):
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> SecurityFragment(

            )
            else -> ActiveFragment()
        }
    }


    override fun getCount() = 2

}