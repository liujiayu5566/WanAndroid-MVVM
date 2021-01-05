package com.castiel.home.http

import com.castiel.common.base.BaseResponse
import com.castiel.home.bean.BannerResponse
import com.castiel.home.bean.HomeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("/banner/json")
    suspend fun netBaner(): BaseResponse<List<BannerResponse>>

    @GET("/article/list/{index}/json")
    suspend fun netHomeList(@Path("index") index: Int): BaseResponse<HomeResponse>
}