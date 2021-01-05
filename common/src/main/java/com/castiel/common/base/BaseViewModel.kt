package com.castiel.common.base

import androidx.lifecycle.*
import com.castiel.common.widget.MultiStateView
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

typealias Block<T> = suspend () -> BaseResponse<T>
typealias Success<T> = (T?) -> Unit
typealias Failure = (BaseResponse<*>) -> Unit
typealias Error = (e: String) -> Unit
typealias Complete = () -> Unit

open class BaseViewModel : ViewModel() {
    var loading: MediatorLiveData<Boolean> = MediatorLiveData()
    var toast: MediatorLiveData<String> = MediatorLiveData()
    var state: MediatorLiveData<MultiStateView.ViewState> = MediatorLiveData()

    protected fun <T> lauch(
        block: Block<T>,
        success: Success<T>,
        failure: Failure? = null,
        error: Error? = null,
        complete: Complete? = null
    ): Job {
        return viewModelScope.launch {
            try {
                val response = block()
                if (response.errorCode == 0) {
                    success(response.data)
                } else {
                    failure?.invoke(response)
                }
            } catch (e: Exception) {
                error?.invoke(e.toString())
            } finally {
                complete?.invoke()
            }
        }
    }
}