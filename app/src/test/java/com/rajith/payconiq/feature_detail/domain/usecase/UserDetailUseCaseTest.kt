package com.rajith.payconiq.feature_detail.domain.usecase

import androidx.paging.PagingData
import com.rajith.payconiq.BaseUT
import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.home.detail.domain.repository.UserDetailRepository
import com.rajith.payconiq.home.detail.domain.usecase.UserDetailUseCase
import com.rajith.payconiq.home.search.domain.model.User
import com.rajith.payconiq.home.search.domain.repository.SearchUserRepository
import com.rajith.payconiq.home.search.domain.usecase.SearchUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class UserDetailUseCaseTest : BaseUT() {

    @MockK
    private lateinit var repository: UserDetailRepository

    @Before
    override fun setUp() {
        MockKAnnotations.init(this)
    }

    override fun isMockServerEnabled() = true

    @Test
    fun `test invoking UserDetailUseCase gives specific user`() = runBlocking {
        // Given
        val usecase = UserDetailUseCase(repository)
        mockHttpResponse("user_detail.json", HttpURLConnection.HTTP_OK)

        // Invoke
        val usersFlow = usecase("")

        // Then
        MatcherAssert.assertThat(usersFlow, CoreMatchers.notNullValue())

        val userDataState = usersFlow.first()
        MatcherAssert.assertThat(userDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(userDataState, CoreMatchers.instanceOf(Resource.Success::class.java))

        val user = (userDataState as Resource.Success<*>).data
        MatcherAssert.assertThat(user, CoreMatchers.notNullValue())
    }
}
