package com.msb.problem.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.castio.common.base.BaseViewModel
import com.castio.common.http.RetrofitClient
import com.castio.common.widget.MultiStateView
import com.msb.problem.bean.ProblemData
import com.msb.problem.bean.ProblemReslut
import com.msb.problem.http.Api

class ProblemViewModel : BaseViewModel() {
    val problemReslutList: MediatorLiveData<List<ProblemData>> = MediatorLiveData()


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
        }, failure = {
            state.postValue(MultiStateView.ViewState.ERROR)
        }, complete = {
            loading.postValue(false)
        })

    }
}