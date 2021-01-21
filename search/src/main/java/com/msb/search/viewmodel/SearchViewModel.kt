package com.msb.search.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.castiel.common.base.BaseViewModel
import com.castiel.common.http.RetrofitClient
import com.castiel.common.widget.MultiStateView
import com.msb.search.bean.SearchHotResponse
import com.msb.search.http.Api

class SearchViewModel : BaseViewModel() {
    var searchHotResponse: MediatorLiveData<List<SearchHotResponse>> = MediatorLiveData()

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
                        searchHotResponse.postValue(it)
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