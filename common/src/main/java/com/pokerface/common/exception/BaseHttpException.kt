package com.pokerface.common.exception

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/16 10:29
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
open class BaseHttpException constructor(
    private val errCode: Int,
    private val errMsg: String,
) : Exception(errMsg) {
//    val isServerException: Boolean
//        get() =this is ServerException
}