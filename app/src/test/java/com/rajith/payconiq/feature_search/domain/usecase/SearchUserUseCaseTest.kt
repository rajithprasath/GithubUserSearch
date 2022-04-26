package com.rajith.payconiq.feature_search.domain.usecase

import com.rajith.payconiq.BaseUT
import com.rajith.payconiq.common.util.Resource
import com.rajith.payconiq.home.search.domain.repository.SearchUserRepository
import com.rajith.payconiq.home.search.domain.usecase.SearchUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class SearchUserUseCaseTest : BaseUT() {

    @MockK
    private lateinit var repository: SearchUserRepository

    @Before
    override fun setUp() {
        MockKAnnotations.init(this)
    }

    override fun isMockServerEnabled() = true

    @Test
    fun `test invoking SearchUserUsecase gives list of users`() = runBlocking {
        // Given
        val usecase = SearchUserUseCase(repository)
        mockHttpResponse("search_users.json", HttpURLConnection.HTTP_OK)

        // Invoke
        val usersListFlow = usecase("")

        // Then
        MatcherAssert.assertThat(usersListFlow, CoreMatchers.notNullValue())

        val usersListDataState = usersListFlow.first()
        MatcherAssert.assertThat(usersListDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            usersListDataState,
            CoreMatchers.instanceOf(Resource.Success::class.java)
        )

        val usersList = (usersListDataState as Resource.Success<*>).data
        MatcherAssert.assertThat(usersList, CoreMatchers.notNullValue())
    }
}
