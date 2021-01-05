package com.castiel.home.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.castiel.common.base.BaseViewModel
import com.castiel.home.http.Api
import com.castiel.common.http.RetrofitClient
import com.castiel.common.widget.MultiStateView
import com.castiel.home.bean.BannerResponse
import kotlinx.coroutines.delay

class HomeViewModel() : BaseViewModel() {
    var bannerResponse: MediatorLiveData<List<BannerResponse>> = MediatorLiveData()

    fun getBanner() {
        lauch({
            RetrofitClient.instance.getApi(Api::class.java).getBaner()
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
                        bannerResponse.postValue(it)
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