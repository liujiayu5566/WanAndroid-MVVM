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
                    state.value = MultiStateView.ViewState.ERROR
                }
                else -> {
                    state.value = MultiStateView.ViewState.CONTENT
                    if (it.datas.isNotEmpty()) {
                        problemReslutList.value?.run {
                            val list = toMutableList()
                            if (index == 0) {
                                list.clear()
                            }
                            list.addAll(it.datas)
                            problemReslutList.value = list
                        } ?: also { _ -> problemReslutList.value = it.datas }
                    }

                    if (problemReslutList.value == null || problemReslutList.value!!.isEmpty())
                        state.value = MultiStateView.ViewState.EMPTY
                }
            }
        }, failure = {
            state.value = MultiStateView.ViewState.ERROR
        }, complete = {
            loading.value = false
        })

    }
}