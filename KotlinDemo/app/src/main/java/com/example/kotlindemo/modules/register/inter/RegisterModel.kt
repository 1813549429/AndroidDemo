package com.example.kotlindemo.modules.register.inter

import android.content.Context

interface RegisterModel {

    fun cancelRequest()

    fun register(
        context: Context,
        username: String,
        password: String,
        rePassword: String,

        // 把结果 给 P层  接口回调
        callback: RegisterPresenter.OnRegisterListener
    )
}