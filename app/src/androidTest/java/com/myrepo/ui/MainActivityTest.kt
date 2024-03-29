package com.myrepo.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.myrepo.R
import org.junit.Rule
import org.junit.Test

class MainActivityTest{

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test() {
        activityScenarioRule.scenario.onActivity { activity: MainActivity? ->
            {

            }
        }
    }

    @Test
    fun testSearchButton_expectedCorrect(){
        onView(withId(R.id.imgSearch)).perform(click())
        onView(withId(R.id.edtSearch)).check(matches(withHint("Search")))
        onView(withId(R.id.edtSearch)).perform(typeText("Test"))
        onView(withId(R.id.edtSearch)).perform(pressImeActionButton(), closeSoftKeyboard())
    }

    @Test
    fun testBackButton_expectedCorrect(){
        onView(withId(R.id.imgSearch)).perform(click())
        onView(withId(R.id.imgBack)).perform(click())
        onView(withId(R.id.edtSearch)).check(matches(withText("")))
    }
}