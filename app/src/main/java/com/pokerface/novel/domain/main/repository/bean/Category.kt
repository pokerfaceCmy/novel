package com.pokerface.novel.domain.main.repository.bean

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/19 9:58
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
data class Category(
    val book_num: Int,
    val category_url: String,
    val cover: String,
    val cover_path: String,
    val create_time: Int,
    val delete_time: Int,
    val id: Int,
    val name: String,
    val page_time: Int,
    val page_url: String,
    val pid: Int,
    val status: Int,
    val update_time: Int
)