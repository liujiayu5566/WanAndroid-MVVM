package com.castio.search.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.castio.common.base.BaseViewModel
import com.castio.common.http.RetrofitClient
import com.castio.common.widget.MultiStateView
import com.castio.search.bean.SearchHotResult
import com.castio.search.http.Api

class SearchViewModel : BaseViewModel() {
    var searchHotResult: MediatorLiveData<List<SearchHotResult>> = MediatorLiveData()

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
                        state.postValue(MultiStateView.ViewState.ERROR)
                    }
                    it.isEmpty() -> {
                        state.postValue(MultiStateView.ViewState.EMPTY)
                    }
                    else -> {
                        state.postValue(MultiStateView.ViewState.CONTENT)
                        searchHotResult.postValue(it)
                    }
                }

            }, failure = {
                toast.postValue(it.errorMsg)
            }, error = {
                state.value = MultiStateView.ViewState.ERROR
            }, complete = {
                loading.postValue(false)
            }
        )
    }


}