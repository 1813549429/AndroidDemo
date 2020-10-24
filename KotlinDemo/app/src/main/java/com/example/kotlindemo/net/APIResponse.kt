package com.example.kotlindemo.net

import android.content.Context
import com.example.kotlindemo.entity.LoginAndRegisterResponseWrapper
import com.example.kotlindemo.view.LoadingDialog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

//  拦截 自定义操作符，目的：包装bean 给拆分成两份 如果成功 data-->UI  如果失败 mes --> UI
abstract class APIResponse<T>(val context : Context) //主构造
    : Observer<LoginAndRegisterResponseWrapper<T>> {

    private var isShow: Boolean = true;

    //次构造
    //在调用主构造的时候会调用这个次构造，并且自动设置isShow参数值为false
    constructor(context: Context, isShow: Boolean = false) : this(context) {
        this.isShow = isShow;
    }


    //  成功
    abstract fun success(data: T ?)
    //  失败
    abstract fun failure(errorMsg: String?)

    //  起点分发的时候
    override fun onSubscribe(d: Disposable) {
        //  弹出 加载框
        if (isShow) {
            LoadingDialog.show(context)
        }
    }

    //  上游流下来的数据
    override fun onNext(t: LoginAndRegisterResponseWrapper<T>) {
        if (t.data == null) {
            //  失败
            failure("登陆失败了，请检查原因：msg:${t.errorMsg}")
        }else {
            //  成功
            success(t.data)
        }

    }

    //上游流下来的错误
    override fun onError(e: Throwable) {
        //取消加载
        LoadingDialog.cancel()
        failure(e.message)
    }

    //  停止
    override fun onComplete() {

        //取消加载
        LoadingDialog.cancel()

    }
}