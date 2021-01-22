package com.castiel.home.http

import com.castiel.common.base.BaseResponse
import com.castiel.home.bean.BannerResult
import com.castiel.home.bean.HomeResult
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("/banner/json")
    suspend fun netBaner(): BaseResponse<List<BannerResult>>

    @GET("/article/list/{index}/json")
    suspend fun netHomeList(@Path("index") index: Int): BaseResponse<HomeResult>
}