package com.rajith.payconiq.home.search.domain.usecase

import androidx.paging.PagingData
import com.rajith.payconiq.home.search.domain.model.UserResponse
import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.home.search.domain.model.User
import com.rajith.payconiq.home.search.domain.repository.SearchUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(
    private val searchUserRepository: SearchUserRepository
) {

    operator fun invoke(searchQuery: String): Flow<PagingData<User>> {
        if (searchQuery.isNullOrEmpty()) {
            return flow { }
        }
        return searchUserRepository.searchUser(searchQuery)
    }
}