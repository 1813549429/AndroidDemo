package com.example.kotlindemo.entity

/**
 * data 登录成功 需要把这个Bean 给 UI

    "data": {
    "admin": false,
    "chapterTops": [],
    "collectIds": [],
    "email": [],
    "icon": "",
    "id": 66720,
    "nickname": "Derry-vip",
    "password": "",
    "publicName": "Derry-vip",
    "token": "",
    "type": 0,
    "username": "Derry-vip"
    }
 */
//String? 允许服务器字段是null
data class LoginAndRegisterResponse(val admin: Boolean,
                                    val chapterTaps: List<*>,//这里的*就是通配符，相当于Java中的?
                                    val collectIds: List<*>,
                                    val email: String ?,
                                    val icon: String?,
                                    val id: String?,
                                    val nickname: String?,
                                    val password: String?,
                                    val publicName: String?,
                                    val token: String?,
                                    val type: Int,
                                    val username: String?
                    )