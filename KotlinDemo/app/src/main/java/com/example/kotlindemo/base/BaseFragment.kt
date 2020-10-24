package com.example.kotlindemo.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


abstract class BaseFragment<P> : Fragment() where P: IBasePresenter{
    lateinit var p: P
    private lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mActivity = context as AppCompatActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        p = getPresenter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createOK()
    }

    abstract fun getPresenter(): P
    override fun onDestroy() {
        super.onDestroy()
        recycle()
    }

    abstract fun recycle()
    abstract fun createOK()

    fun hideActionBar() : Unit {
        var actionBar: ActionBar? = mActivity?.supportActionBar
        actionBar?.hide()
    }

    fun showActionBar() : Unit {
        mActivity?.supportActionBar?.show()
    }
}