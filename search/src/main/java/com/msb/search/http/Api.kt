package com.msb.search.http

import com.castiel.common.base.BaseResponse
import com.msb.search.bean.SearchHotResponse
import retrofit2.http.GET

interface Api {

    @GET("/hotkey/json")
    suspend fun netSearchHot(): BaseResponse<List<SearchHotResponse>>

}