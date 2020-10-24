package com.example.kotlindemo.modules.personal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

class PersonalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(activity, "个人", Toast.LENGTH_SHORT).show()
        val root : View? = null
        return root ?: super.onCreateView(inflater, container, savedInstanceState)
    }
}