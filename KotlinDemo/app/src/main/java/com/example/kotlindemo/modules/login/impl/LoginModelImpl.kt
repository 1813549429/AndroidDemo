package com.example.kotlindemo.modules.login.impl

import android.content.Context
import android.util.Log
import com.example.kotlindemo.api.WanAndroidApi
import com.example.kotlindemo.entity.LoginAndRegisterResponse
import com.example.kotlindemo.modules.login.inter.LoginModel
import com.example.kotlindemo.modules.login.inter.LoginPresenter
import com.example.kotlindemo.net.APIClient
import com.example.kotlindemo.net.APIResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

// Model 层 的 实现
class LoginModelImpl : LoginModel{

    //取消请求
    override fun cancelRequest() {


    }

    override fun login(
        context: Context,
        username: String,
        password: String,
        onLoginListener: LoginPresenter.OnLoginListener
    ) {

        APIClient.instance.instanceRetrofit(WanAndroidApi::class.java)
                    // RxJava知识
                    .loginAction(username, password)
                    .subscribeOn(Schedulers.io()) //给上面请求服务器的操作分配异步线程
                    .observeOn(AndroidSchedulers.mainThread()) //给下面切换回主线程
                    .subscribe(object : APIResponse<LoginAndRegisterResponse>(context) {
                        override fun success(data: LoginAndRegisterResponse?) {
                            //  成功  data UI
                            Log.e("LoginModelImpl", "success: $data")
                            onLoginListener.loginSuccess(data)
                        }

                        override fun failure(errorMsg: String?) {
                            //  失败  msg UI
                            Log.e("LoginModelImpl", "error: $errorMsg")
                            onLoginListener.loginFailure(errorMsg)
                        }


                    })

    }
}