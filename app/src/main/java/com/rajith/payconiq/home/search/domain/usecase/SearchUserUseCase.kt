package com.rajith.payconiq.home.search.domain.usecase

import com.rajith.payconiq.common.domain.model.UserResponse
import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.home.search.domain.repository.SearchUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchUserUseCase (
    private val searchUserRepository: SearchUserRepository
) {

    operator fun invoke(searchQuery: String, page: Int): Flow<Resource<UserResponse>> {
        if (page == 0) {
            return flow { }
        }
        return searchUserRepository.searchUser(searchQuery, page)
    }
}