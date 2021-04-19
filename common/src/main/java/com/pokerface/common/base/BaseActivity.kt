package com.pokerface.common.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.interfaces.XPopupCallback
import com.pokerface.common.IUIActionEventObserver
import com.pokerface.common.LoadingDialogSimple
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.lang.reflect.ParameterizedType

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/13 10:42
 * @Desc: Activity的基类
 * @GitHub：https://github.com/pokerfaceCmy
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(),
    IUIActionEventObserver {
    protected lateinit var binding: VB

    override val lifecycleSupportedScope: CoroutineScope
        get() = lifecycleScope

    override val mContext: Context?
        get() = this

    override val mLifecycleOwner: LifecycleOwner
        get() = this

    private lateinit var job: Job

    private val loadingDialog: BasePopupView? by lazy {
        XPopup.Builder(this)
            .dismissOnTouchOutside(false)
            .dismissOnBackPressed(true)
            .setPopupCallback(object : XPopupCallback {
                override fun onCreated(popupView: BasePopupView?) {}

                override fun beforeShow(popupView: BasePopupView?) {}

                override fun onShow(popupView: BasePopupView?) {}

                override fun onDismiss(popupView: BasePopupView?) {
                    //如果已完成任务,正常销毁dialog
                    if (this@BaseActivity::job.isInitialized) {
                        if (job.isCompleted) {
                            return
                        } else {//取消任务,弹出提示
                            job.cancel()
                            showSuccessSnackBar("已取消")
                        }
                    }
                }

                override fun beforeDismiss(popupView: BasePopupView?) {
                }

                override fun onBackPressed(popupView: BasePopupView?): Boolean {
                    return false
                }

                override fun onKeyBoardStateChanged(popupView: BasePopupView?, height: Int) {}

                override fun onDrag(
                    popupView: BasePopupView?,
                    value: Int,
                    percent: Float,
                    upOrLeft: Boolean
                ) {
                }

            })
            .asCustom(LoadingDialogSimple(this))

    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz0 = type.actualTypeArguments[0] as Class<VB>
        val method = clazz0.getMethod("inflate", LayoutInflater::class.java)
        binding = method.invoke(null, layoutInflater) as VB
        setContentView(binding.root)

        init()
    }


    abstract fun init()

    override fun showLoading() {
        loadingDialog?.takeIf { it.isDismiss }?.show()
    }

    override fun showLoading(job: Job?) {
        if (job != null) {
            this.job = job
        }
        showLoading()
    }

    override fun dismissLoading() {
        loadingDialog?.takeIf { it.isShow }?.dismiss()
    }

    override fun showToast(msg: String) {

    }

    override fun showSuccessSnackBar(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG)
            .show()
    }

    override fun showErrorSnackBar(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG)
            .setTextColor(Color.parseColor("#E06C75"))
            .show()
    }

}