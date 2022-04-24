package com.rajith.payconiq.home.search.di

import com.rajith.payconiq.home.search.data.repository.SearchUserRepositoryImpl
import com.rajith.payconiq.home.search.domain.repository.SearchUserRepository
import com.rajith.payconiq.home.search.domain.usecase.SearchUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class SearchUserModule {

    @Provides
    fun provideSearchUserRepository(searchUserRepositoryImpl: SearchUserRepositoryImpl): SearchUserRepository {
        return searchUserRepositoryImpl
    }

    @Provides
    @Singleton
    fun provideSearchUserUseCase(searchUserRepository: SearchUserRepository): SearchUserUseCase {
        return SearchUserUseCase(searchUserRepository)
    }
}