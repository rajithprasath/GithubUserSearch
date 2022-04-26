package com.rajith.payconiq.di

import com.rajith.payconiq.home.detail.data.remote.UserDetailService
import com.rajith.payconiq.home.detail.data.repository.UserDetailRepositoryImpl
import com.rajith.payconiq.home.detail.domain.repository.UserDetailRepository
import com.rajith.payconiq.home.detail.domain.usecase.UserDetailUseCase
import com.rajith.payconiq.home.search.data.remote.SearchUserService
import com.rajith.payconiq.home.search.data.repository.SearchUserRepositoryImpl
import com.rajith.payconiq.home.search.domain.repository.SearchUserRepository
import com.rajith.payconiq.home.search.domain.usecase.SearchUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestNetworkModule {


    @Provides
    @Singleton
    fun provideSearchUserService(retrofit: Retrofit): SearchUserService =
        retrofit.create(SearchUserService::class.java)

    @Provides
    @Singleton
    fun provideUserDetailService(retrofit: Retrofit): UserDetailService =
        retrofit.create(UserDetailService::class.java)

    @Provides
    fun provideUserDetailRepository(userDetailRepositoryImpl: UserDetailRepositoryImpl): UserDetailRepository {
        return userDetailRepositoryImpl
    }

    @Provides
    fun provideUserDetailUseCase(userDetailRepository: UserDetailRepository): UserDetailUseCase {
        return UserDetailUseCase(userDetailRepository)
    }

    @Provides
    fun provideSearchUserRepository(searchUserRepositoryImpl: SearchUserRepositoryImpl): SearchUserRepository {
        return searchUserRepositoryImpl
    }

    @Provides
    fun provideSearchUserUseCase(searchUserRepository: SearchUserRepository): SearchUserUseCase {
        return SearchUserUseCase(searchUserRepository)
    }
}