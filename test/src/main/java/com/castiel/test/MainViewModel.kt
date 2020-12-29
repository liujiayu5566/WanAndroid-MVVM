package com.castiel.test

import androidx.lifecycle.ViewModel
import com.castiel.common.base.BaseViewModel
import com.castiel.common.http.RetrofitClient

class MainViewModel : BaseViewModel() {

    fun getBanner() {
        lauch(
            {
                val result = RetrofitClient.instance.getApi().getBaner()
                println(result.toString())
            },
            {
                println(it.toString())

            }

        )
    }
}