package com.castio.search.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.castio.common.base.BaseViewModel
import com.castio.common.http.RetrofitClient
import com.castio.common.widget.MultiStateView
import com.castio.search.bean.SearchResultListData
import com.castio.search.http.Api

class SearchResultModel : BaseViewModel() {
    var searchResult: MediatorLiveData<List<SearchResultListData>> = MediatorLiveData()

    /**
     * 搜索结果数据
     */
    fun netSearch(index: Int, k: String) {
        lauch({
            RetrofitClient.instance.getApi(Api::class.java).netSearch(index, k)
        }, {
            when (it) {
                null -> {
                    state.postValue(MultiStateView.ViewState.ERROR)
                }
                else -> {
                    state.postValue(MultiStateView.ViewState.CONTENT)
                    searchResult.value?.run {
                        val list = toMutableList()
                        if (index == 0) {
                            list.clear()
                        }
                        list.addAll(it.datas)
                        searchResult.postValue(list)
                    } ?: searchResult.postValue(it.datas)
                }
            }
        }, failure = {
            toast.postValue(it.errorMsg)
        }, complete = {
            loading.postValue(false)
        })
    }

}