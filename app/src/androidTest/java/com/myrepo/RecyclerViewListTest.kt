package com.myrepo

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.myrepo.ui.MainActivity
import com.myrepo.ui.adapter.AdapterTrending
import org.junit.Rule
import org.junit.Test

class RecyclerViewListTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test() {
        activityScenarioRule.scenario.onActivity { _: MainActivity? ->
        }
    }

    @Test
    fun itemWithText_doestNotExist(){
        onView(withId(R.id.rvTrendingList)).perform(RecyclerViewActions.actionOnItemAtPosition<AdapterTrending.ViewHolder>(0, click()))
        onView(withId(R.id.rvTrendingList)).perform(RecyclerViewActions.actionOnItemAtPosition<AdapterTrending.ViewHolder>(3, click()))
        onView(withId(R.id.rvTrendingList)).perform(RecyclerViewActions.actionOnItemAtPosition<AdapterTrending.ViewHolder>(3, click()))
        onView(withId(R.id.rvTrendingList)).perform(RecyclerViewActions.actionOnItemAtPosition<AdapterTrending.ViewHolder>(4, click()))

    }
}