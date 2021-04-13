package com.pokerface.common

import android.content.Context
import androidx.lifecycle.LifecycleOwner
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
}