package com.example.kotlindemo.api

import com.example.kotlindemo.entity.LoginAndRegisterResponse
import com.example.kotlindemo.entity.LoginAndRegisterResponseWrapper
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

//客户端API 可以访问 服务器的API
interface WanAndroidApi {
    /**
     * 登陆API
     *
     */
    @POST("/user/login")
    @FormUrlEncoded
    fun loginAction(@Field("username") username:String,
                    @Field("password") password:String)
    :Observable<LoginAndRegisterResponseWrapper<LoginAndRegisterResponse>>  //返回值

    /**
     * 注册API
     *
     */
    @POST("/user/register")
    @FormUrlEncoded
    fun registerAction(@Field("username") username:String,
                    @Field("password") password:String,
                    @Field("repassword") rePassword:String)
    :Observable<LoginAndRegisterResponseWrapper<LoginAndRegisterResponse>>  //返回值



}