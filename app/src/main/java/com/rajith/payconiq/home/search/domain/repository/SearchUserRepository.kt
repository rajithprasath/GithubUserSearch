package com.rajith.payconiq.home.search.domain.repository

import com.rajith.payconiq.home.search.domain.model.UserResponse
import com.rajith.payconiq.common.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchUserRepository {

    fun searchUser(query: String, pageNumber: Int): Flow<Resource<UserResponse>>
}