package com.rajith.payconiq.feature_detail.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rajith.payconiq.MainCoroutinesRule
import com.rajith.payconiq.home.detail.domain.usecase.UserDetailUseCase
import com.rajith.payconiq.home.detail.viewmodel.UserDetailViewModel
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UserDetailViewModelTest {

    // Subject under test
    private lateinit var viewModel: UserDetailViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @MockK
    lateinit var userDetailUseCase: UserDetailUseCase

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
        viewModel = UserDetailViewModel(userDetailUseCase)

        // Then
        coVerify(exactly = 1) { userDetailUseCase.invoke("") }
    }
}
