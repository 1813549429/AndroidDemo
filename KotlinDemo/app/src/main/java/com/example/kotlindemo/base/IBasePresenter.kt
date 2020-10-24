package com.example.kotlindemo.base

//所有P层的最上层父类    最上层标准
interface IBasePresenter {

    //视图离开了  (Activity，Fragment) 做一些销毁的事情
    fun unAttachView()
}