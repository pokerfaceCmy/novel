package com.pokerface.novel.domain.main.repository.remote

import com.pokerface.novel.domain.main.repository.bean.Category
import retrofit2.http.GET

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/19 9:56
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
interface BookService {
    @GET("category")
    suspend fun getCategory(): MutableList<Category>
}