package com.example.kotlindemo.modules.register.impl

import android.content.Context
import com.example.kotlindemo.entity.LoginAndRegisterResponse
import com.example.kotlindemo.modules.register.inter.RegisterPresenter
import com.example.kotlindemo.modules.register.inter.RegisterView

class RegisterPresenterImpl(var registerView : RegisterView?): RegisterPresenter , RegisterPresenter.OnRegisterListener {

    private val registerModel:RegisterModelImpl = RegisterModelImpl()


    override fun registerWanAndroid(
        context: Context,
        username: String,
        password: String,
        rePassword: String
    ) {

        registerModel.register(context, username, password, rePassword, this)
    }

    override fun unAttachView() {
        registerView = null
        registerModel.cancelRequest()
    }

    override fun registerSuccess(registerBean: LoginAndRegisterResponse?) {
        registerView?.registerSuccess(registerBean)
    }

    override fun registerFailed(errorMsg: String?) {
        registerView?.registerFailed(errorMsg)

    }
}