package com.rajith.payconiq.home.search.domain.repository

import androidx.paging.PagingData
import com.rajith.payconiq.home.search.domain.model.User
import kotlinx.coroutines.flow.Flow

interface SearchUserRepository {

    fun searchUser(query: String): Flow<PagingData<User>>
}