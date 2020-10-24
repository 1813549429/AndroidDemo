package com.example.kotlindemo.modules.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kotlindemo.MainActivity
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.entity.LoginAndRegisterResponse
import com.example.kotlindemo.modules.login.impl.LoginPresenterImpl
import com.example.kotlindemo.modules.login.inter.LoginPresenter
import com.example.kotlindemo.modules.login.inter.LoginView
import com.example.kotlindemo.modules.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginPresenter>() , LoginView{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        hideActionBar()
        user_login_bt.setOnClickListener(onClickListen)
        user_register_tv.setOnClickListener(onClickListen)
    }

    private val onClickListen = View.OnClickListener {  view ->

        when(view.id) {
            R.id.user_login_bt -> {
                val userNameStr = user_phone_et.text.toString()
                val userPwdStr = user_password_et.text.toString()
                presenter.loginAction(this, userNameStr, userPwdStr)

            }

            R.id.user_register_tv -> {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }

    }

    override fun loginSuccess(loginAndRegisterBean: LoginAndRegisterResponse?) {
        Toast.makeText(this@LoginActivity, "登陆成功😊", Toast.LENGTH_SHORT).show();
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun loginFailure(errorMsg: String?) {
        Toast.makeText(this@LoginActivity, "登陆失败😭", Toast.LENGTH_SHORT).show();

    }

    override fun createP(): LoginPresenter = LoginPresenterImpl(this)


    override fun recycle() {
        presenter.unAttachView()
    }
}
