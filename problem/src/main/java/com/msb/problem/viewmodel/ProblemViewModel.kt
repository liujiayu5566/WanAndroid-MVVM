package com.msb.problem.viewmodel

import androidx.lifecycle.MediatorLiveData
import com.castio.common.base.BaseViewModel
import com.castio.common.http.RetrofitClient
import com.castio.common.widget.MultiStateView
import com.msb.problem.bean.ProblemListData
import com.msb.problem.http.Api

class ProblemViewModel : BaseViewModel() {
    val problemReslutList: MediatorLiveData<List<ProblemListData>> = MediatorLiveData()


    fun netProblemList(index: Int) {
        lauch({
            RetrofitClient.instance.getApi(Api::class.java).netProblemList(index)
        }, {
            when (it) {
                null -> {
                    state.postValue(MultiStateView.ViewState.ERROR)
                }
                else -> {
                    state.postValue(MultiStateView.ViewState.CONTENT)
                    if (it.datas.isNotEmpty()) {
                        problemReslutList.value?.run {
                            val list = toMutableList()
                            if (index == 0) {
                                list.clear()
                            }
                            list.addAll(it.datas)
                            problemReslutList.postValue(list)
                        } ?: problemReslutList.postValue(it.datas)
                    }
                }
            }
        }, failure = {
            state.postValue(MultiStateView.ViewState.ERROR)
        }, complete = {
            loading.postValue(false)
        })

    }
}