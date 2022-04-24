package com.rajith.payconiq.home.search.data.repository

import com.rajith.payconiq.common.domain.model.UserResponse
import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.home.search.data.remote.SearchUserService
import com.rajith.payconiq.home.search.domain.repository.SearchUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchUserRepositoryImpl @Inject constructor(
    private val api: SearchUserService
) : SearchUserRepository {

    override fun searchUser(searchQuery: String, pageNumber: Int): Flow<Resource<UserResponse>> = flow {
        emit(Resource.Loading())
        val users: UserResponse
        try {
            users = api.searchUser(query = searchQuery, pageNumber = pageNumber)
            emit(Resource.Success(users))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
    }


}