package com.DanMarius.AdvancedNoteTakingApp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainPageActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainPageActivity::class.java)

    @Test
    fun ensureRecyclerViewAndAddNoteButtonAreDisplayed() {
        // Check if the RecyclerView for notes is displayed
        onView(withId(R.id.notesRecyclerView)).check(matches(isDisplayed()))

        // Check if the floating action button to add a new note is displayed
        onView(withId(R.id.addButton)).check(matches(isDisplayed()))

    }

}
