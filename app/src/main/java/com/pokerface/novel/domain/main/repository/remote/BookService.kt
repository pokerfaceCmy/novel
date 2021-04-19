package com.pokerface.novel.domain.main.repository.remote

import com.pokerface.novel.domain.main.repository.bean.Category
import com.pokerface.novel.domain.main.repository.bean.CategoryDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/19 9:56
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
interface BookService {
    @GET("category")
    suspend fun getCategory(): MutableList<Category>

    @GET("lists")
    suspend fun getCategoryDetail(@Query("id" ) id: Int): CategoryDetail

}