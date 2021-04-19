package com.pokerface.common.action

import kotlinx.coroutines.Job

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/16 10:23
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */

open class BaseActionEvent

class ShowLoadingEvent(val job: Job?) : BaseActionEvent()

class ShowLoadingWithoutJobEvent() : BaseActionEvent()

class ShowToastEvent(val message: String) : BaseActionEvent()

class DismissLoadingEvent : BaseActionEvent()