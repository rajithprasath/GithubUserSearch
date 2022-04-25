package com.rajith.payconiq.home.detail.data.remote

import com.rajith.payconiq.home.detail.domain.model.UserInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface UserDetailService {

    @GET("users/{username}")
    suspend fun getDetails(@Path("username") username: String): UserInfo

}