package com.example.kotlindemo.modules.login.impl

import android.content.Context
import com.example.kotlindemo.entity.LoginAndRegisterResponse
import com.example.kotlindemo.modules.login.inter.LoginPresenter
import com.example.kotlindemo.modules.login.inter.LoginView

class LoginPresenterImpl(var loginView: LoginView?) : LoginPresenter , LoginPresenter.OnLoginListener{

    // 需要 M 去请求服务器
    private val loginModel: LoginModelImpl = LoginModelImpl()

    // 需要 V 去更新UI

    override fun loginAction(context: Context, username: String, password: String) {
        loginModel.login(context, username, password, this)
    }

    //Base Presenter的
    override fun unAttachView() {

        loginView = null
        loginModel.cancelRequest()
    }

    override fun loginSuccess(loginAndRegisterBean: LoginAndRegisterResponse?) {

        loginView?.loginSuccess(loginAndRegisterBean)
    }

    override fun loginFailure(errorMsg: String?) {

        loginView?.loginFailure(errorMsg)
    }
}