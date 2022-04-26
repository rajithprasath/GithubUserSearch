package com.rajith.payconiq

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

abstract class BaseTest {

    protected lateinit var mockServer: MockWebServer

    @Before
    open fun setUp() {
        configureMockServer()
    }

    @After
    open fun tearDown() {
        stopMockServer()
    }

    abstract fun isMockServerEnabled(): Boolean // Because we don't want it always enabled on all tests

    private fun configureMockServer() {

            mockServer = MockWebServer()
            mockServer.start()

    }

    private fun stopMockServer() {
        if (isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }

    fun getMockUrl() = mockServer.url("/").toString()

}