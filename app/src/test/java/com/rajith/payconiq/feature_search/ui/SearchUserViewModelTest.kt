package com.rajith.payconiq.feature_search.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rajith.payconiq.MainCoroutinesRule
import com.rajith.payconiq.home.search.domain.usecase.SearchUserUseCase
import com.rajith.payconiq.home.search.viewmodel.SearchUserViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SearchUserViewModelTest {

    // Subject under test
    private lateinit var viewModel: SearchUserViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @MockK
    lateinit var searchUserUsecase: SearchUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test when SearchUserViewModel is initialized`() = runBlocking {

        // Invoke
        viewModel = SearchUserViewModel(searchUserUsecase)

        // Then
        coVerify(exactly = 1) { searchUserUsecase.invoke("") }
    }
}
