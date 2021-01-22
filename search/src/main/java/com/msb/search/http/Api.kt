package com.msb.search.http

import com.castiel.common.base.BaseResponse
import com.msb.search.bean.SearchHotResult
import com.msb.search.bean.SearchResult
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