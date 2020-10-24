package com.example.kotlindemo.entity

/**
 * 包装Bean
    登陆成功返回的Json数据
    {
        "data": {
        "admin": false,
        "chapterTops": [],
        "collectIds": [],
        "email": "",
        "icon": "",
        "id": 66720,
        "nickname": "Derry-vip",
        "password": "",
        "publicName": "Derry-vip",
        "token": "",
        "type": 0,
        "username": "Derry-vip"
        },
        "errorCode": 0,
        "errorMsg": ""
    }
    登陆错误返回的json数据
    {
        "data": null,
        "errorCode": -1,
        "errorMsg": "账号密码不匹配！"
    }
 */
data class LoginAndRegisterResponseWrapper<T>(val data: T, val errorCode: Int, val errorMsg: String)