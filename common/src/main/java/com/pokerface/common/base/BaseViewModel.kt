package com.pokerface.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokerface.common.IUIActionEventObserver.IViewModelActionEvent
import com.pokerface.common.action.DismissLoadingEvent
import com.pokerface.common.action.ShowLoadingEvent
import com.pokerface.common.action.ShowLoadingWithoutJobEvent
import com.pokerface.common.action.ShowToastEvent
import com.pokerface.common.callback.BaseRequestCallback
import com.pokerface.common.callback.RequestCallback
import com.pokerface.common.exception.BaseHttpException
import kotlinx.coroutines.*

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/16 10:25
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
open class BaseViewModel : ViewModel(), IViewModelActionEvent {

    override fun showSuccessSnackBar(msg: String) {
    }

    override fun showErrorSnackBar(msg: String) {

    }

    override val lifecycleSupportedScope: CoroutineScope
        get() = viewModelScope

    override val showLoadingEventLD = MutableLiveData<ShowLoadingEvent>()

    override val dismissLoadingEventLD = MutableLiveData<DismissLoadingEvent>()

    override val showToastEventLD = MutableLiveData<ShowToastEvent>()

    override val showLoadingWithoutJobEventLD = MutableLiveData<ShowLoadingWithoutJobEvent>()


    fun <Data> enqueue(
        apiFun: suspend () -> Data,
        showLoading: Boolean = true,
        showErrorMsg: Boolean = true,
        callbackFun: (RequestCallback<Data>.() -> Unit)? = null
    ): Job {
        return lifecycleSupportedScope.launch(Dispatchers.Main) {
            val callback = if (callbackFun == null) null else RequestCallback<Data>().apply {
                callbackFun.invoke(this)
            }
            try {
                if (showLoading) {
                    showLoading(coroutineContext[Job])
                }
                callback?.onStart?.invoke()
                val response: Data?
                try {
                    response = apiFun.invoke()
                } catch (ex: Exception) {
                    handleException(ex, callback)
                    return@launch
                }
                onGetResponse(callback, response)
            } finally {
                try {
                    callback?.onFinally?.invoke()
                } finally {
                    if (showLoading) {
                        dismissLoading()
                    }
                }
            }
        }
    }


    private suspend fun <Data> onGetResponse(callback: RequestCallback<Data>?, httpData: Data?) {
        callback?.let {
            withContext(NonCancellable) {
                callback.onSuccess?.let {
                    withContext(Dispatchers.Main) {
                        it.invoke(httpData)
                    }
                }
                callback.onSuccessIO?.let {
                    withContext(Dispatchers.IO) {
                        it.invoke(httpData)
                    }
                }
            }
        }
    }

    private fun handleException(ex: Exception, callback: BaseRequestCallback?) {
        callback?.let {
            if (ex is CancellationException) {
                callback.onCancelled?.invoke()
                return
            }
            val exception = generateException(ex)
            callback.onFailed?.invoke(exception)
        }
    }

    private fun generateException(ex: Exception): BaseHttpException {
        ex.printStackTrace()

        return if (ex is CancellationException) {
            BaseHttpException(404, "")
        } else {
            BaseHttpException(404, "")
        }

    }

}