package com.rajith.payconiq.feature_detail.domain.data.remote.api

import com.rajith.payconiq.MainCoroutinesRule
import com.rajith.payconiq.home.detail.data.remote.UserDetailService
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.net.HttpURLConnection

class UserDetailServiceTest : ApiAbstract<UserDetailService>() {

    private lateinit var apiService: UserDetailService

    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Before
    override fun setUp() {
        apiService = createService(UserDetailService::class.java)
    }

    @After
    override fun tearDown() {
    }

    override fun isMockServerEnabled() = true

    @Throws(IOException::class)
    @Test
    fun `test getDetails() returns User`() = runBlocking {
        // Given
        mockHttpResponse("detail_user.json", HttpURLConnection.HTTP_OK)

        // Invoke
        val response = apiService.getDetails("rajithprasath")
        mockWebServer.takeRequest()

        // Then
        assertThat(response.login, `is`("rajithprasath"))
        assertThat(response.name, `is`("null"))
    }
}
