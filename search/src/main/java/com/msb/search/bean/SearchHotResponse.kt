package com.msb.search.bean

data class SearchHotResponse(
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)