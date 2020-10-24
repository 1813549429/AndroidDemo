package com.example.kotlindemo.modules.login.inter

import com.example.kotlindemo.entity.LoginAndRegisterResponse

//View层
interface LoginView {
    //把结果显示到 Activity / Fragment
    fun loginSuccess(loginAndRegisterBean: LoginAndRegisterResponse ? )


    fun loginFailure(errorMsg: String ? )

}