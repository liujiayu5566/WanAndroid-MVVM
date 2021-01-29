package com.castio.home.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.castio.common.base.BaseViewModel
import com.castio.home.http.Api
import com.castio.common.http.RetrofitClient
import com.castio.common.widget.MultiStateView
import com.castio.home.bean.BannerResult
import com.castio.home.bean.HomeListData
import java.util.concurrent.CountDownLatch

class HomeViewModel() : BaseViewModel() {
    var bannerResult: MediatorLiveData<List<BannerResult>> = MediatorLiveData()
    var homeResponse: MediatorLiveData<List<HomeListData>> = MediatorLiveData()
    private var placedList: ArrayList<HomeListData> = arrayListOf()
    private var countDownLatch: CountDownLatch? = null

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
                when (it) {
                    null -> {
                        state.postValue(MultiStateView.ViewState.ERROR)
                    }
                    else -> {
                        countDownLatch = countDownLatch?.run {
                            await()
                            null
                        }
                        state.postValue(MultiStateView.ViewState.CONTENT)
                        val value = homeResponse.value?.also { homeList ->
                            val list = homeList.toMutableList()
                            if (index == 0) {
                                list.clear()
                                if (placedList.size > 0) list.addAll(placedList)
                            }
                            if (it.datas.isNotEmpty())
                                list.addAll(it.datas)
                            homeResponse.postValue(list)
                        }
                        if (value == null) {
                            if (it.datas.isNotEmpty())
                                placedList.addAll(it.datas)
                            homeResponse.postValue(placedList)
                        }
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

    private fun netPlacedList() {
        lauch({
            RetrofitClient.instance.getApi(Api::class.java).netPlacedList()
        },
            {
                when (it) {
                    null -> {
                        state.postValue(MultiStateView.ViewState.ERROR)
                    }
                    else -> {
                        state.postValue(MultiStateView.ViewState.CONTENT)
                        if (it.isNotEmpty()) {
                            it.map { model ->
                                model.isTop = true
                            }
                            placedList.clear()
                            placedList.addAll(it)
                        }
                    }
                }
            }, complete = {
                countDownLatch?.countDown()
            }
        )
    }

    fun netPlacedAndHomeList() {
        countDownLatch?.run {
            return
        }
        countDownLatch = CountDownLatch(1)
        netPlacedList()
        netHomeList(0)
    }

}