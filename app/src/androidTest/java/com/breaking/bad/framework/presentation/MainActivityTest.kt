package com.breaking.bad.framework.presentation

import androidx.fragment.app.ListFragment
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.breaking.bad.R
import com.breaking.bad.business.data.network.NetworkDataSource
import com.breaking.bad.di.NetworkModule
import com.breaking.bad.framework.datasource.api.FakeCharacterApi
import com.breaking.bad.framework.presentation.util.MainFragmentFactory
import com.breaking.bad.utils.Constants.EMPTY_LIST
import com.breaking.bad.utils.EspressoIdlingResourceRule
import com.breaking.bad.utils.JsonUtil
import com.breaking.bad.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(NetworkModule::class)
class MainActivityTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Inject
    lateinit var jsonUtil: JsonUtil

    @Inject
    lateinit var fragmentFactory: MainFragmentFactory //Just simple way to configure, should be done with hilt

    private lateinit var fakeCharacterApi: FakeCharacterApi

    @Inject
    lateinit var networkDataSource: NetworkDataSource

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testEmptyList() = runBlocking {
        fakeCharacterApi = FakeCharacterApi(jsonUtil)
        fakeCharacterApi.charactersJsonFileName = EMPTY_LIST
        val list = fakeCharacterApi.getCharacters()
        assertTrue(list.isEmpty())
    }

    @Test
    fun testDataGeneration() = runBlocking {
        val list = networkDataSource.getCharacters()
        assertFalse(list.isEmpty())
    }

    @Test
    fun mainFragmentTest() {
        val scenario = launchFragmentInHiltContainer<ListFragment>(
            factory = fragmentFactory
        ) // just showing how to do it with hilt and fragment factory ...
    }

    @Test
    fun generalEndToEndTest() {

        val scenario = launchActivity<MainActivity>()

        val recyclerView = onView(withId(R.id.characters_recycler_view))
        val progressBar = onView(withId(R.id.progress_bar))

        //Confirm ListFragment is in view
        recyclerView.check(matches(isDisplayed()))

        //Confirm the data is loaded and progress bar is gone
        progressBar.check(matches(not(isDisplayed())))

        // Select a note from the list
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<CharactersListAdapter.ViewHolder>(0, ViewActions.click())
        )

        // Confirm DetailFragment is in view
        onView(withId(R.id.details_scroll_view)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_name))
            .check(matches(withText("Walter White - Heisenberg")))
        onView(withId(R.id.tv_status))
            .check(matches(withText("Presumed dead")))
        onView(withId(R.id.tv_occupation))
            .check(matches(withText("Occupation: High School Chemistry Teacher, Meth King Pin")))
        onView(withId(R.id.tv_appearance))
            .check(matches(withText("Appeared in seasons: 1, 2, 3, 4, 5")))

        // press back button
        onView(isRoot()).perform(ViewActions.pressBack())

        // confirm NoteListFragment is in view
        recyclerView.check(matches(isDisplayed()))
    }
}
























