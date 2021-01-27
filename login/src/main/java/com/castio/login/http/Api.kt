package com.castio.login.http

import com.castio.common.base.BaseResponse
import com.castio.login.bean.LoginResult
import retrofit2.http.*

interface Api {
    //登陆
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun netLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseResponse<LoginResult>


    @FormUrlEncoded
    @POST("/user/register")
    suspend fun netRsgister(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") rePassword: String
    ): BaseResponse<LoginResult>
}