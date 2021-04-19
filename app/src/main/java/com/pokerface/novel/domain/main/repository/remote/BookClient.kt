package com.pokerface.novel.domain.main.repository.remote

import javax.inject.Inject

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/19 9:56
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
class BookClient @Inject constructor(
    private val bookService: BookService,
) {
    suspend fun getCategory() = bookService.getCategory()

    suspend fun getCategoryDetail(id: Int) = bookService.getCategoryDetail(id)
}