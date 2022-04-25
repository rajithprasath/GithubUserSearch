package com.rajith.payconiq.home.detail.data.repository

import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.home.detail.data.remote.UserDetailService
import com.rajith.payconiq.home.detail.domain.model.UserInfo
import com.rajith.payconiq.home.detail.domain.repository.UserDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserDetailRepositoryImpl @Inject constructor(
    private val api: UserDetailService
) : UserDetailRepository {


    override fun getUserDetails(username: String): Flow<Resource<UserInfo>> = flow {
        emit(Resource.Loading())
        val userInfo: UserInfo
        try {
            userInfo = api.getDetails(username = username)
            emit(Resource.Success(userInfo))
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