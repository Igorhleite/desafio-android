package com.picpay.desafio.android.ui.fragment

import android.content.Context
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.SmallTest
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.launchFragmentInHiltContainer
import com.picpay.desafio.android.ui.adapter.UserAdapter.UserViewHolder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@SmallTest
class HomeFragmentTest {
    private lateinit var testContext: Context

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var fragment: HomeFragment

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragment()
        initAdapter()
    }

    @Test
    fun fragmentMustBeInitializedAndShowTitle() {
        onView(withId(R.id.tv_title))
            .check(matches(withText(R.string.title)))
    }

    @Test
    fun recyclerViewMustBeInitializedAndShowListOfUsers() {
        onView(withId(R.id.rc_users))
            .perform()
            .check(matches(isDisplayed()))
    }

    @Test
    fun recyclerViewMustBeInitializedAndPossibleScroll() {
        onView(withId(R.id.rc_users))
            .perform(
                RecyclerViewActions.scrollToPosition<UserViewHolder>(
                    50
                )
            )
    }

    @Test
    fun recyclerViewMustBeInitializedAndPossibleClickOnItemShareButton() {
        onView((withId(R.id.rc_users)))
            .perform(
                actionOnItemAtPosition<UserViewHolder>(
                    10,
                    clickOnItemOnRecyclerView(R.id.ib_share)
                )
            )
    }

    @Test
    fun recyclerViewMustBeInitializedAndPossibleSeeDetailScreenOnClickItem() {
        onView(withId(R.id.rc_users))
            .perform(actionOnItemAtPosition<UserViewHolder>(0, click()))
        onView(withId(R.id.detail_dialog))
            .perform()
            .check(matches(isDisplayed()))
    }

    private fun initAdapter() {
        fragment.userAdapter
            .submitList(getMockOfUsers())
    }

    private fun launchFragment() {
        launchFragmentInHiltContainer<HomeFragment> {
            fragment = this as HomeFragment
            testContext = this.requireContext()
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.homeFragment)
            this.viewLifecycleOwnerLiveData.observeForever { lifeCycle ->
                if (lifeCycle != null) {
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }
        }
    }

    private fun getMockOfUsers(): List<User> {
        val user = User(
            "1",
            "Sandrine Spinka",
            "Tod86",
            "https://randomuser.me/api/portraits/men/1.jpg"
        )
        val list = mutableListOf<User>()
        for (i in 1..50) {
            list.add(user)
        }
        return list
    }

    private fun clickOnItemOnRecyclerView(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController?, view: View) {
                val v: View = view.findViewById(id)
                v.performClick()
            }
        }
    }
}