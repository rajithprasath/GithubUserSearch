package com.rajith.payconiq.home.detail.domain.usecase

import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.home.detail.domain.model.UserInfo
import com.rajith.payconiq.home.detail.domain.repository.UserDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDetailUseCase @Inject constructor(
    private val userDetailRepository: UserDetailRepository
) {

    operator fun invoke(username: String): Flow<Resource<UserInfo>> {
        if (username.isNullOrEmpty()) {
            return flow { }
        }
        return userDetailRepository.getUserDetails(username)
    }
}