package com.example.kotlindemo.modules.register.impl

import android.content.Context
import android.util.Log
import com.example.kotlindemo.api.WanAndroidApi
import com.example.kotlindemo.entity.LoginAndRegisterResponse
import com.example.kotlindemo.modules.register.inter.RegisterPresenter
import com.example.kotlindemo.modules.register.inter.RegisterModel
import com.example.kotlindemo.net.APIClient
import com.example.kotlindemo.net.APIResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RegisterModelImpl : RegisterModel{

    // 取消请求
    override fun cancelRequest() {}

    override fun register(
        context: Context,
        username: String,
        password: String,
        repassword: String,
        callback: RegisterPresenter.OnRegisterListener
    ) {
        // 网络模型
        APIClient.instance.instanceRetrofit(WanAndroidApi::class.java)
            .registerAction(username, password, repassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : APIResponse<LoginAndRegisterResponse>(context) {
                override fun success(data: LoginAndRegisterResponse?) {
                    Log.e("RegisterModelImpl", "success: $data")
                    callback.registerSuccess(data)
                }

                override fun failure(errorMsg: String?) {
                    Log.e("RegisterModelImpl", "error: $errorMsg")
                    callback.registerFailed(errorMsg)
                }

            })
    }

}