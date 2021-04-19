package com.pokerface.novel.domain.main.repository

import com.pokerface.novel.domain.main.repository.remote.BookClient
import javax.inject.Inject

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/19 9:55
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
class MainRepository @Inject constructor(
    private val bookClient: BookClient
) {
    suspend fun getCategory() = bookClient.getCategory()
    suspend fun getCategoryDetail(id: Int) = bookClient.getCategoryDetail(id)
}