package com.pokerface.novel.domain.main.di

import com.pokerface.novel.domain.main.repository.MainRepository
import com.pokerface.novel.domain.main.repository.remote.BookClient
import com.pokerface.novel.domain.main.repository.remote.BookService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/19 10:07
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
@Module
@InstallIn(ApplicationComponent::class)
object NetWorkModule {
    @Provides
    @Singleton
    fun provideBookClient(bookService: BookService): BookClient {
        return BookClient(bookService)
    }

    @Provides
    @Singleton
    fun provideBookService(retrofit: Retrofit): BookService {
        return retrofit.create(BookService::class.java)
    }

    fun provideMainRepository(BookClient: BookClient): MainRepository {
        return MainRepository(BookClient)
    }
}