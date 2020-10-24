package com.example.kotlindemo.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlindemo.modules.login.impl.LoginPresenterImpl

abstract class BaseActivity<P> : AppCompatActivity() where P : IBasePresenter{

    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createP()

    }

    fun hideActionBar() {
        //任何Java代码东西，必须使用 ? 允许为null，来接受，防止空指针异常
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
    }

    fun showActionBar() {
        supportActionBar?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        recycle()
    }
    abstract fun createP(): P
    abstract fun recycle()
}