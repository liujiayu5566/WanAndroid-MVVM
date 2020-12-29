package com.castiel.home.http

import com.castiel.common.base.BaseResponse
import com.castiel.home.bean.BannerResponse
import retrofit2.http.GET

interface Api {

    @GET("/banner/json")
    suspend fun getBaner(): BaseResponse<List<BannerResponse>>
}