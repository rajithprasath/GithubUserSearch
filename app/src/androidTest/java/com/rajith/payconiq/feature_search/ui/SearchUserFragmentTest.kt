package com.rajith.payconiq.feature_search.ui

import android.widget.AutoCompleteTextView
import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.filters.LargeTest
import com.rajith.payconiq.base.BaseIT
import com.rajith.payconiq.R
import com.rajith.payconiq.feature_search.utils.hasItemCount
import com.rajith.payconiq.feature_search.utils.waitForAdapterChangeWithPagination
import com.rajith.payconiq.home.MainActivity
import org.hamcrest.CoreMatchers.*
import org.junit.*
import org.junit.runner.RunWith
import java.net.HttpURLConnection


@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchUserFragmentTest : BaseIT() {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)

    @get:Rule
    var executorRule = CountingTaskExecutorRule()

    override fun isMockServerEnabled() = true

    @Before
    override fun setUp() {
        super.setUp()
        activityRule.launchActivity(null)
    }

    @Test
    fun whenFragmentIsEmpty() {
        onView(withId(R.id.tvPlaceholder)).check(matches(isDisplayed()))
        onView(withId(R.id.retry_button)).check(matches(not(isDisplayed())))
    }

    @Test
    fun whenUserSearchUsersAndSucceed() {
        mockHttpResponse("search_users.json", HttpURLConnection.HTTP_OK)
        onView(withId(R.id.etSearch)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("r"))
        waitForAdapterChangeWithPagination(getRecyclerView(), executorRule, 4)

        onView(withId(R.id.rvUserSearch)).check(matches((hasItemCount(1))))
        onView(allOf(withId(R.id.etSearch), withText("rajithprasath"))).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun whenUserSearchUsersAndFailed() {
        mockHttpResponse("search_users.json", HttpURLConnection.HTTP_BAD_REQUEST)

        onView(withId(R.id.etSearch)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("t"))
        Thread.sleep(1000)
        onView(withId(R.id.tvPlaceholder)).check(matches(isDisplayed()))
        onView(withId(R.id.retry_button)).check(matches(isDisplayed()))
    }


    /**
     * Convenient access to String resources
     */
    private fun getString(id: Int) = activityRule.activity.getString(id)

    /**
     * Convenient access to [SearchUserFragment]'s RecyclerView
     */
    private fun getRecyclerView() =
        activityRule.activity.findViewById<RecyclerView>(R.id.rvUserSearch)
}