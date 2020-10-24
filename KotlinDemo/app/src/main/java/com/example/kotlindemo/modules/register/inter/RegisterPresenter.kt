package com.example.kotlindemo.modules.register.inter

import android.content.Context
import com.example.kotlindemo.base.IBasePresenter
import com.example.kotlindemo.entity.LoginAndRegisterResponse

interface RegisterPresenter : IBasePresenter{
    fun registerWanAndroid(context: Context, username: String, password: String, rePassword: String)

    // M  ---》 P  接口监听
    interface OnRegisterListener {

        fun registerSuccess(registerBean: LoginAndRegisterResponse?)

        fun registerFailed(errorMsg: String?)

    }
}