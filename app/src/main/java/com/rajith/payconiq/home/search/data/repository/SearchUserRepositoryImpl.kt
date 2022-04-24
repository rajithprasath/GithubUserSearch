package com.rajith.payconiq.home.search.data.repository

import com.rajith.payconiq.common.domain.model.UserResponse
import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.home.search.data.remote.SearchUserService
import com.rajith.payconiq.home.search.domain.repository.SearchUserRepository
import kotlinx.coroutines.flow.Flow

class SearchUserRepositoryImpl (
    private val api: SearchUserService
) : SearchUserRepository {

    override fun searchUser(query: String, pageNumber: Int): Flow<Resource<UserResponse>> {
        TODO("Not yet implemented")
    }


}