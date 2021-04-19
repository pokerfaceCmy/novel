package com.pokerface.novel.domain.main.repository.bean

import com.google.gson.annotations.SerializedName

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/19 11:29
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
data class CategoryDetail(
    val current_page: Int,
    @SerializedName("data")
    val categoryDetailDataList: MutableList<CategoryDetailData>
)

data class CategoryDetailData(
    val author: String,
    val browse: Int,
    val cid: Int,
    val cover_path: String,
    val create_time: Int,
    val id: Int,
    val name: String,
    val status: Int
)