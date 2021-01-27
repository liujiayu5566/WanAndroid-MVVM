package com.castio.home.http

import com.castio.common.base.BaseResponse
import com.castio.home.bean.BannerResult
import com.castio.home.bean.HomeResult
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("/banner/json")
    suspend fun netBaner(): BaseResponse<List<BannerResult>>

    @GET("/article/list/{index}/json")
    suspend fun netHomeList(@Path("index") index: Int): BaseResponse<HomeResult>
}