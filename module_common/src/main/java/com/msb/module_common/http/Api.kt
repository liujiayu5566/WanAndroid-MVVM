package com.msb.module_common.http

import com.castio.common.base.BaseResponse
import com.msb.module_common.bean.LoginResult
import retrofit2.http.*

interface Api {
    //登陆
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun netLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): BaseResponse<LoginResult>

    //注册
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun netRsgister(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") rePassword: String
    ): BaseResponse<LoginResult>


}