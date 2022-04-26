package com.rajith.payconiq.home.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rajith.payconiq.home.search.domain.model.UserResponse
import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.home.search.domain.model.User
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

     fun searchUser(query: String): Flow<PagingData<User>> {
        return searchUserUseCase(query)
            .cachedIn(viewModelScope)
            .flowOn(Dispatchers.IO)
    }
}