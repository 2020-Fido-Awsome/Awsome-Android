package com.entersekt.fido2.fragment_tutorial

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.entersekt.fido2.R
import com.entersekt.fido2.appdata.DataManage
import com.entersekt.fido2.ui.MainActivity
import com.entersekt.fido2.ui.ResistActivity
import com.entersekt.fido2.ui.TutorialActivity
import kotlinx.android.synthetic.main.activity_store.*
import kotlinx.android.synthetic.main.fragment_tutorial_fifth.*
import kotlinx.android.synthetic.main.fragment_tutorial_third.*
import java.time.temporal.TemporalUnit

class FifthFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        nick_sub_btn.setOnClickListener(View.OnClickListener {
//            val intent = Intent(activity, MainActivity::class.java)
//            startActivity(intent)
//        })


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutorial_fifth, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        nick_sub_btn.setOnClickListener {

                activity?.let {
                    if (text_nick.text.isNullOrBlank()) {
                        Toast.makeText(context, "닉네임은 필수입니다.", Toast.LENGTH_SHORT).show();
                    } else {

                        val intent = Intent(context, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
            }
        }

    }


}