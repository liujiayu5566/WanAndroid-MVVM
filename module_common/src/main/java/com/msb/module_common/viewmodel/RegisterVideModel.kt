package com.msb.module_common.viewmodel

import androidx.lifecycle.MutableLiveData
import com.castio.common.base.BaseViewModel
import com.castio.common.http.RetrofitClient
import com.msb.module_common.bean.LoginResult
import com.msb.module_common.http.Api

class RegisterVideModel : BaseViewModel() {
    val loginResult: MutableLiveData<LoginResult> = MutableLiveData<LoginResult>()

    /**
     * 注册账号
     */
    fun netRsgister(userName: String, password: String) {
        loading.value = true
        lauch({
            RetrofitClient.instance.getApi(Api::class.java)
                .netRsgister(userName, password, password)
        }, {
            when (it) {
                null -> {

                }
                else -> {
                    loginResult.value = it
                }
            }
        }, complete = {
            loading.value = false
        })

    }
}