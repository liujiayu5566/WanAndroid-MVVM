package com.msb.module_common.viewmodel

import androidx.lifecycle.MutableLiveData
import com.castio.common.base.BaseViewModel
import com.castio.common.http.RetrofitClient
import com.msb.module_common.bean.LoginResult
import com.msb.module_common.http.Api

class LoginVideModel : BaseViewModel() {
    val loginResult: MutableLiveData<LoginResult> = MutableLiveData<LoginResult>()

    fun netLogin(userName: String, password: String) {
        loading.postValue(true)
        lauch({
            RetrofitClient.instance.getApi(Api::class.java).netLogin(userName, password)
        }, {
            when (it) {
                null -> {

                }
                else -> {
                    loginResult.postValue(it)
                }
            }
        }, complete = {
            loading.postValue(false)
        })

    }
}