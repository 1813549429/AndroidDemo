package com.example.kotlindemo.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    private object Holder {
        val INSTANCE = APIClient()
    }

    //派生
    companion object {
        val instance = Holder.INSTANCE;
    }

    fun <T> instanceRetrofit(apiInstance : Class<T>) : T {

        //OkHttpClient请求服务器
        val mOkHttpClient = OkHttpClient().newBuilder()
                //添加读取超时时间
            .readTimeout(10000, TimeUnit.SECONDS)
                //添加连接超时时间
            .connectTimeout(10000, TimeUnit.SECONDS)
                //添加写出超时时间
            .writeTimeout(10000, TimeUnit.SECONDS)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://www.wanandroid.com")
            //请求方
            .client(mOkHttpClient)
            //响应方
            //Response的事情
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //RxJava来处理
            .addConverterFactory(GsonConverterFactory.create()) //Gson来解析
            .build()

        return retrofit.create(apiInstance)
    }
}