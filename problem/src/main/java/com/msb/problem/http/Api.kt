package com.msb.problem.http

import com.castio.common.base.BaseResponse
import com.msb.problem.bean.ProblemReslut
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("/wenda/list/{index}/json ")
    suspend fun netProblemList(@Path("index") index: Int): BaseResponse<ProblemReslut>
}