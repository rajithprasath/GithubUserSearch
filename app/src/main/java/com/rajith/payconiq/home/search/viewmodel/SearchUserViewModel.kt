package com.rajith.payconiq.home.search.viewmodel

import androidx.lifecycle.ViewModel
import com.rajith.payconiq.common.domain.model.UserResponse
import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.home.search.domain.usecase.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    val searchUserUseCase: SearchUserUseCase
) : ViewModel() {

    suspend fun searchUser(): Flow<Resource<UserResponse>> {
        return searchUserUseCase("q", 1)
            .flowOn(Dispatchers.IO)
    }
}