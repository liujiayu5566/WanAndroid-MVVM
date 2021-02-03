package com.castio.home.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.castio.common.base.BaseViewModel
import com.castio.home.http.Api
import com.castio.common.http.RetrofitClient
import com.castio.common.widget.MultiStateView
import com.castio.home.bean.BannerResult
import com.castio.home.bean.HomeListData
import com.castio.home.bean.HomeResult
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
                        state.value = MultiStateView.ViewState.CONTENT
                        bannerResult.value = it
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


    fun netHomeList(index: Int) {
        lauch({
            RetrofitClient.instance.getApi(Api::class.java).netHomeList(index)
        },
            {
                when (it) {
                    null -> {
                        state.value = MultiStateView.ViewState.ERROR
                    }
                    else -> {
                        countDownLatch = countDownLatch?.run {
                            await()
                            null
                        }

                        state.value = MultiStateView.ViewState.CONTENT
                        homeResponse.value?.run {
                            val list = toMutableList()
                            if (index == 0) {
                                list.clear()
                                if (placedList.size > 0) list.addAll(placedList)
                            }
                            if (it.datas.isNotEmpty())
                                list.addAll(it.datas)
                            homeResponse.value = list
                        } ?: run {
                            if (it.datas.isNotEmpty())
                                placedList.addAll(it.datas)
                            homeResponse.value = placedList
                        }

                        if (homeResponse.value == null || homeResponse.value!!.isEmpty())
                            state.value = MultiStateView.ViewState.EMPTY

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

    private fun netPlacedList() {
        lauch({
            RetrofitClient.instance.getApi(Api::class.java).netPlacedList()
        },
            {
                when (it) {
                    null -> {
                        state.value = MultiStateView.ViewState.ERROR
                    }
                    else -> {
                        state.value = MultiStateView.ViewState.CONTENT
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