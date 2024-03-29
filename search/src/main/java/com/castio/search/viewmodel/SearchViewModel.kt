package com.castio.search.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.castio.common.base.BaseViewModel
import com.castio.common.http.RetrofitClient
import com.castio.common.widget.MultiStateView
import com.castio.search.bean.SearchHotResult
import com.castio.search.bean.SearchResultListData
import com.castio.search.http.Api

class SearchViewModel : BaseViewModel() {
    var searchHotResult: MediatorLiveData<List<SearchHotResult>> = MediatorLiveData()
    var searchResult: MediatorLiveData<List<SearchResultListData>> = MediatorLiveData()


    /**
     * 搜索热词
     */
    fun netSearchHot() {
        lauch({
            RetrofitClient.instance.getApi(Api::class.java).netSearchHot()
        },
            {
                when {
                    it == null -> {
                        state.value = MultiStateView.ViewState.ERROR
                    }
                    it.isEmpty() -> {
                        state.value = MultiStateView.ViewState.EMPTY
                    }
                    else -> {
                        state.value = MultiStateView.ViewState.CONTENT
                        searchHotResult.value = it
                    }
                }

            }, failure = {
                toast.value = it.errorMsg
            }, error = {
                state.value = MultiStateView.ViewState.ERROR
            }, complete = {
                loading.value = false
            }
        )
    }

    /**
     * 搜索结果数据
     */
    fun netSearch(index: Int, k: String) {
        lauch({
            RetrofitClient.instance.getApi(Api::class.java).netSearch(index, k)
        }, {
            when (it) {
                null -> {
                    state.value = MultiStateView.ViewState.ERROR
                }
                else -> {
                    state.value = MultiStateView.ViewState.CONTENT
                    searchResult.value?.run {
                        val list = toMutableList()
                        if (index == 0) {
                            list.clear()
                        }
                        list.addAll(it.datas)
                        searchResult.value = list
                    } ?: also { _ -> searchResult.value = it.datas }

                    if (searchResult.value == null || searchResult.value!!.isEmpty())
                        state.value = MultiStateView.ViewState.EMPTY
                }
            }
        }, failure = {
            toast.value = it.errorMsg
        }, complete = {
            loading.value = false
        })

    }
}