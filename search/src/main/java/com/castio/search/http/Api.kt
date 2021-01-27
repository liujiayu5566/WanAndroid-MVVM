package com.castio.search.http

import com.castio.common.base.BaseResponse
import com.castio.search.bean.SearchHotResult
import com.castio.search.bean.SearchResult
import retrofit2.http.*

interface Api {

    @GET("/hotkey/json")
    suspend fun netSearchHot(): BaseResponse<List<SearchHotResult>>

    @FormUrlEncoded
    @POST("/article/query/{index}/json")
    suspend fun netSearch(
        @Path("index") index: Int,
        @Field("k") k: String
    ): BaseResponse<SearchResult>

}