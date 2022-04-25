package com.rajith.payconiq.home.detail.domain.repository

import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.home.detail.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserDetailRepository {

    fun getUserDetails(username: String): Flow<Resource<UserInfo>>
}