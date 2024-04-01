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
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.testing.WorkManagerTestInitHelper
import com.myrepo.MyApp
import com.myrepo.R
import com.myrepo.base.BackupWorker
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit


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

    @Test
    fun textPeriodicText_expectCorrect(){

        // Create periodic work request
        val periodicWorkRequest = PeriodicWorkRequestBuilder<BackupWorker>(15, TimeUnit.MINUTES).build()

        // Enqueue periodic request
        val workManager =WorkManager.getInstance(MyApp.INSTANCE.applicationContext)
        workManager.enqueueUniquePeriodicWork(
            "UpdateRecord",
            ExistingPeriodicWorkPolicy.KEEP,periodicWorkRequest
        )
        // Initialize testDriver
        val testDriver = WorkManagerTestInitHelper.getTestDriver(MyApp.INSTANCE.applicationContext)

        workManager.enqueue(periodicWorkRequest).result.get()
        testDriver?.setPeriodDelayMet(periodicWorkRequest.id)
        // Get WorkInfo and outputData
        val workInfo = workManager.getWorkInfoById(periodicWorkRequest.id).get()

        // Assert
        TestCase.assertEquals(workInfo.state, WorkInfo.State.ENQUEUED)
    }
}