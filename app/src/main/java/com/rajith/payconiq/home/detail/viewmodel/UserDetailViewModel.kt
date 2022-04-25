package com.rajith.payconiq.home.detail.viewmodel

import androidx.lifecycle.ViewModel
import com.rajith.payconiq.home.search.domain.model.UserResponse
import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.home.detail.domain.model.UserInfo
import com.rajith.payconiq.home.detail.domain.usecase.UserDetailUseCase
import com.rajith.payconiq.home.search.domain.usecase.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    val userDetailUseCase: UserDetailUseCase
) : ViewModel() {

    fun getUserDetail(username: String): Flow<Resource<UserInfo>> {
        return userDetailUseCase(username)
            .flowOn(Dispatchers.IO)
    }
}