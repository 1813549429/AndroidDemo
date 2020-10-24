package com.example.kotlindemo.modules.register.inter

import com.example.kotlindemo.entity.LoginAndRegisterResponse

interface RegisterView {
    // 成功 失败 显示 到 Activty

    fun registerSuccess(registerBean: LoginAndRegisterResponse?)

    fun registerFailed(errorMsg: String?)
}