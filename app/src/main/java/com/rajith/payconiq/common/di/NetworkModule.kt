package com.rajith.payconiq.common.di

import com.rajith.payconiq.home.search.data.remote.SearchUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideSearchUserService(retrofit: Retrofit): SearchUserService =
        retrofit.create(SearchUserService::class.java)
}