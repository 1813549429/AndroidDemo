package com.example.kotlindemo.modules.login.inter

import android.content.Context
import com.example.kotlindemo.base.IBasePresenter
import com.example.kotlindemo.entity.LoginAndRegisterResponse

interface LoginPresenter : IBasePresenter{

    //登陆
    fun loginAction(context: Context, username: String, password: String)

    //监听回调
    interface OnLoginListener {
        fun loginSuccess(loginAndRegisterBean: LoginAndRegisterResponse? )
        fun loginFailure(error: String ? )
    }
}