package com.pokerface.novel.http

import com.pokerface.common.bean.IApiResult

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/19 10:09
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */

data class BookWrapper<Data>(
    val code: Int,
    val msg: String,
    val data : Data
) : IApiResult<Data> {
    override val isSuccess: Boolean
        get() = code == 200
    override val httpData: Data
        get() = data
    override val httpMsg: String
        get() = msg
    override val httpCode: Int
        get() = code
    override val dataField: String
        get() = "data"
}