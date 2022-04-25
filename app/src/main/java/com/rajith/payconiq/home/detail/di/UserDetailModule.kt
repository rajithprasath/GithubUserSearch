package com.rajith.payconiq.home.detail.di

import com.rajith.payconiq.home.detail.data.repository.UserDetailRepositoryImpl
import com.rajith.payconiq.home.detail.domain.repository.UserDetailRepository
import com.rajith.payconiq.home.detail.domain.usecase.UserDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserDetailModule {

    @Provides
    fun provideUserDetailRepository(userDetailRepositoryImpl: UserDetailRepositoryImpl): UserDetailRepository {
        return userDetailRepositoryImpl
    }

    @Provides
    fun provideUserDetailUseCase(userDetailRepository: UserDetailRepository): UserDetailUseCase {
        return UserDetailUseCase(userDetailRepository)
    }
}