package com.pokerface.common

import android.content.Context
import com.lxj.xpopup.core.CenterPopupView

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/13 10:48
 * @Desc: 默认的Loading Dialog
 * @GitHub：https://github.com/pokerfaceCmy
 */
class LoadingDialogSimple(context: Context) : CenterPopupView(context) {
    override fun getImplLayoutId(): Int {
        return R.layout.dialog_loading
    }

}