package com.pokerface.novel

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/16 10:53
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
@HiltAndroidApp
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        initLog()
    }

    private fun initLog() {
        Logger.addLogAdapter(AndroidLogAdapter())
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}