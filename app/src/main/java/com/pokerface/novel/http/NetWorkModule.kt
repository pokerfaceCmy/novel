package com.pokerface.novel.http

import com.google.gson.Gson
import com.pokerface.common.converter.RetroGsonConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @Author: pokerfaceCmy
 * @Date: 2021/4/19 10:08
 * @Desc: TODO
 * @GitHub：https://github.com/pokerfaceCmy
 */
@Module
@InstallIn(ApplicationComponent::class)
class NetWorkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(1000L, TimeUnit.MILLISECONDS)
            .writeTimeout(1000L, TimeUnit.MILLISECONDS)
            .connectTimeout(1000L, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(logInterceptor)
            .retryOnConnectionFailure(true)

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://book.talers.xyz/")
            .addConverterFactory(RetroGsonConverterFactory.create(gson, BookWrapper::class.java))
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}