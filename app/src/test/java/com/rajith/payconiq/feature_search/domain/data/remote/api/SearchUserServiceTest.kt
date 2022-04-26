
package com.rajith.payconiq.feature_search.domain.data.remote.api

import com.rajith.payconiq.MainCoroutinesRule
import com.rajith.payconiq.home.search.data.remote.SearchUserService
import junit.framework.Assert

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.io.IOException
import java.net.HttpURLConnection

class SearchUserServiceTest : ApiAbstract<SearchUserService>() {

    private lateinit var apiService: SearchUserService


    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Before
    override fun setUp() {
        apiService = createService(SearchUserService::class.java)
        MockitoAnnotations.initMocks(this)
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    override fun tearDown() {
    }

    override fun isMockServerEnabled() = true

    @Throws(IOException::class)
    @Test
    fun `test searchUser() returns list of Users`() = runBlocking {
        // Given
        mockHttpResponse("search_users.json", HttpURLConnection.HTTP_OK)

        // Invoke
        val response = apiService.searchUser("rajithprasath", 1, 10)
        mockWebServer.takeRequest()

        // Then
        assertThat(response.total_count, `is`(1))
        assertThat(response.incomplete_results, `is`(false))
        assertThat(response.items.size, `is`(1))

        assertThat(response.items.first().login, `is`("rajithprasath"))
    }
}
