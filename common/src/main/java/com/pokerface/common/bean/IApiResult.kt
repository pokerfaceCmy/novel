package com.pokerface.common.bean

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/19 10:11
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
interface IApiResult<Data> {
    /**
     * 是否请求成功
     */
    val isSuccess: Boolean

    /**
     * 返回的数据
     */
    val httpData: Data

    /**
     * 返回的msg
     */
    val httpMsg: String

    /**
     * 返回的错误码
     */
    val httpCode: Int

    /**
     * data字段的Json名
     */
    val dataField: String
}