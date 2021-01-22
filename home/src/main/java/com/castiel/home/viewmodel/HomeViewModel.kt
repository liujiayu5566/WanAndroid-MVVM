package com.castiel.home.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.castiel.common.base.BaseViewModel
import com.castiel.home.http.Api
import com.castiel.common.http.RetrofitClient
import com.castiel.common.widget.MultiStateView
import com.castiel.home.bean.BannerResult
import com.castiel.home.bean.HomeListData

class HomeViewModel() : BaseViewModel() {
    var bannerResult: MediatorLiveData<List<BannerResult>> = MediatorLiveData()
    var homeResponse: MediatorLiveData<List<HomeListData>> = MediatorLiveData()
    var complete = MediatorLiveData<Any>()

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