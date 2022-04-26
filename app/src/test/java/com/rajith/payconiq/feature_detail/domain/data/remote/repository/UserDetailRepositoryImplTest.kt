package com.rajith.payconiq.feature_detail.domain.data.remote.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rajith.payconiq.BaseUT
import com.rajith.payconiq.MainCoroutinesRule
import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.home.detail.data.remote.UserDetailService
import com.rajith.payconiq.home.detail.data.repository.UserDetailRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class UserDetailRepositoryTest : BaseUT() {

    // Subject under test
    private lateinit var repository: UserDetailRepositoryImpl

    @MockK
    private lateinit var apiService: UserDetailService


    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    override fun tearDown() {
    }

    override fun isMockServerEnabled() = true


    @Test
    fun `test getUserDetails() gives User`() = runBlocking {
        // Given
        repository = UserDetailRepositoryImpl(apiService)
        // Given
        mockHttpResponse("user_detail.json", HttpURLConnection.HTTP_OK)
        val response = apiService.getDetails("rajithprasath")

        // When
        coEvery { response }
            .returns(response)

        // Invoke
        val apiResponseFlow = repository.getUserDetails("")

        // Then
        MatcherAssert.assertThat(apiResponseFlow, CoreMatchers.notNullValue())

        val userListDataState = apiResponseFlow.first()
        MatcherAssert.assertThat(userListDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            userListDataState,
            CoreMatchers.instanceOf(Resource.Success::class.java)
        )

        val userList = (userListDataState as Resource.Success<*>).data
        MatcherAssert.assertThat(userList, CoreMatchers.notNullValue())

        coVerify(exactly = 1) { apiService.getDetails(any()) }
        confirmVerified(apiService)
    }

    @Test(expected = HttpException::class)
    fun `getUser by username and fail`() {
        mockHttpResponse("user_detail.json", HttpURLConnection.HTTP_FORBIDDEN)
        runBlocking {
            repository.getUserDetails("FAKE")
        }
    }
}
