package com.pokerface.common

import android.content.Context
import androidx.lifecycle.*
import com.pokerface.common.action.DismissLoadingEvent
import com.pokerface.common.action.ShowLoadingEvent
import com.pokerface.common.action.ShowLoadingWithoutJobEvent
import com.pokerface.common.action.ShowToastEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/13 10:47
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
interface ICoroutineEvent {
    /**
     * 此字段用于声明在 BaseViewModel，BaseRemoteDataSource，BaseView 下和生命周期绑定的协程作用域
     * 推荐的做法是：
     * 1.BaseView 单独声明自己和 View 相关联的作用域
     * 2.BaseViewModel 单独声明自己和 ViewModel 相关联的作用域，因为一个 BaseViewModel 可能和多个 BaseView 相关联，所以不要把 BaseView 的 CoroutineScope 传给 BaseViewModel
     * 3.BaseRemoteDataSource 首选使用 BaseViewModel 传过来的 lifecycleCoroutineScope，因为 BaseRemoteDataSource 和 BaseViewModel 是一对一的关系
     */
    val lifecycleSupportedScope: CoroutineScope
}

interface IUIActionEvent : ICoroutineEvent {
    fun showLoading()

    fun showLoading(job: Job?)

    fun dismissLoading()

    fun showToast(msg: String)

    fun showSuccessSnackBar(msg: String)

    fun showErrorSnackBar(msg: String)
}

interface IUIActionEventObserver : IUIActionEvent {
    val mContext: Context?
    val mLifecycleOwner: LifecycleOwner

    fun <VM> getViewModel(clazz: Class<VM>,
                          factory: ViewModelProvider.Factory? = null,
                          initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null): Lazy<VM> where VM : ViewModel, VM : IViewModelActionEvent {
        return lazy {
            getViewModelFast(clazz, factory, initializer)
        }
    }

    fun <VM> getViewModelFast(clazz: Class<VM>,
                              factory: ViewModelProvider.Factory? = null,
                              initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null): VM where VM : ViewModel, VM : IViewModelActionEvent {
        return when (val localValue = mLifecycleOwner) {
            is ViewModelStoreOwner -> {
                if (factory == null) {
                    ViewModelProvider(localValue).get(clazz)
                } else {
                    ViewModelProvider(localValue, factory).get(clazz)
                }
            }
            else -> {
                factory?.create(clazz) ?: clazz.newInstance()
            }
        }.apply {
            generateActionEvent(this)
            initializer?.invoke(this, mLifecycleOwner)
        }
    }

    fun <VM> generateActionEvent(viewModel: VM) where VM : ViewModel, VM : IViewModelActionEvent {
        viewModel.showLoadingEventLD.observe(mLifecycleOwner, Observer {
            this@IUIActionEventObserver.showLoading(it.job)
        })
        viewModel.dismissLoadingEventLD.observe(mLifecycleOwner, Observer {
            this@IUIActionEventObserver.dismissLoading()
        })
        viewModel.showToastEventLD.observe(mLifecycleOwner, Observer {
            if (it.message.isNotBlank()) {
                this@IUIActionEventObserver.showToast(it.message)
            }
        })
    }

    interface IViewModelActionEvent : IUIActionEvent {

        val showLoadingEventLD: MutableLiveData<ShowLoadingEvent>

        val showLoadingWithoutJobEventLD: MutableLiveData<ShowLoadingWithoutJobEvent>

        val dismissLoadingEventLD: MutableLiveData<DismissLoadingEvent>

        val showToastEventLD: MutableLiveData<ShowToastEvent>

        override fun showLoading(job: Job?) {
            showLoadingEventLD.value = ShowLoadingEvent(job)
        }

        override fun dismissLoading() {
            dismissLoadingEventLD.value = DismissLoadingEvent()
        }

        override fun showToast(msg: String) {
            showToastEventLD.value = ShowToastEvent(msg)
        }

        override fun showLoading() {
            showLoadingWithoutJobEventLD.value = ShowLoadingWithoutJobEvent()
        }

        override fun showSuccessSnackBar(msg: String) {
            TODO("Not yet implemented")
        }

        override fun showErrorSnackBar(msg: String) {
            TODO("Not yet implemented")
        }

    }
}