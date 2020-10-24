package com.example.kotlindemo.modules.register

import android.os.Bundle
import android.widget.Toast
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.entity.LoginAndRegisterResponse
import com.example.kotlindemo.modules.register.inter.RegisterPresenter
import com.example.kotlindemo.modules.register.inter.RegisterView
import com.example.kotlindemo.modules.register.impl.RegisterPresenterImpl
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity<RegisterPresenter>(), RegisterView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        hideActionBar()
        user_register_bt.setOnClickListener {
            val usernameStr = user_phone_et.text.toString()
            val passwordStr = user_password_et.text.toString()
            presenter.registerWanAndroid(this, usernameStr, passwordStr, passwordStr)
        }
    }

    override fun createP(): RegisterPresenter = RegisterPresenterImpl(this)

    override fun recycle() {
        presenter.unAttachView()
    }

    override fun registerSuccess(registerBean: LoginAndRegisterResponse?) {
        Toast.makeText(this, "注册成功😊", Toast.LENGTH_SHORT).show()
    }

    override fun registerFailed(errorMsg: String?) {
        Toast.makeText(this, "注册失败😭", Toast.LENGTH_SHORT).show()
    }
}