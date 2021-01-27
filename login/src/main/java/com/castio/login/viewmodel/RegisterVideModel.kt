package com.castio.login.viewmodel

import androidx.lifecycle.MutableLiveData
import com.castio.common.base.BaseViewModel
import com.castio.common.http.RetrofitClient
import com.castio.login.bean.LoginResult
import com.castio.login.http.Api

class RegisterVideModel : BaseViewModel() {
    val loginResult: MutableLiveData<LoginResult> = MutableLiveData<LoginResult>()

    /**
     * 注册账号
     */
    fun netRsgister(userName: String, password: String) {
        loading.postValue(true)
        lauch({
            RetrofitClient.instance.getApi(Api::class.java)
                .netRsgister(userName, password, password)
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