package com.castio.home.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.castio.common.base.BaseViewModel
import com.castio.home.http.Api
import com.castio.common.http.RetrofitClient
import com.castio.common.widget.MultiStateView
import com.castio.home.bean.BannerResult
import com.castio.home.bean.HomeListData

class HomeViewModel() : BaseViewModel() {
    var bannerResult: MediatorLiveData<List<BannerResult>> = MediatorLiveData()
    var homeResponse: MediatorLiveData<List<HomeListData>> = MediatorLiveData()

    fun netBanner() {
        lauch({
            RetrofitClient.instance.getApi(Api::class.java).netBaner()
        },
            {
                when {
                    it == null -> {

                    }
                    it.isEmpty() -> {

                    }
                    else -> {
                        state.postValue(MultiStateView.ViewState.CONTENT)
                        bannerResult.postValue(it)
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


    fun netHomeList(index: Int) {
        lauch({
            RetrofitClient.instance.getApi(Api::class.java).netHomeList(index)
        },
            {
                when {
                    it == null -> {
                        state.postValue(MultiStateView.ViewState.ERROR)
                    }
                    it.datas.isEmpty() -> {

                    }
                    else -> {
                        state.postValue(MultiStateView.ViewState.CONTENT)
                        homeResponse.value?.run {
                            val list = toMutableList()
                            if (index == 0) {
                                list.clear()
                            }
                            list.addAll(it.datas)
                            homeResponse.postValue(list)
                        } ?: homeResponse.postValue(it.datas)
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