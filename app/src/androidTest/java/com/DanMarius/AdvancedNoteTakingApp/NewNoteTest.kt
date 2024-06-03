package com.DanMarius.AdvancedNoteTakingApp

import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewNoteTest {

    @Test
    fun addNote_savesNote() {
        // Launch the AddNoteActivity
        launch(NewNote::class.java).use {
            // Type text into the title EditText
            onView(withId(R.id.titleEditText)).perform(typeText(" Title Test Note 1"))
            // Type text into the content EditText
            onView(withId(R.id.contentEditText)).perform(typeText("Content test 1."))
            // Click the "Save" button
            onView(withId(R.id.saveButton)).perform(click())


        }
        launch(NewNote::class.java).use {
            // Type text into the title EditText
            onView(withId(R.id.titleEditText)).perform(typeText(" Title Test Note 2"))
            // Type text into the content EditText
            onView(withId(R.id.contentEditText)).perform(typeText("Content test 2."))
            // Click the "Save" button
            onView(withId(R.id.saveButton)).perform(click())



        }
        launch(NewNote::class.java).use {
            // Type text into the title EditText
            onView(withId(R.id.titleEditText)).perform(typeText("Title Test Note 3"))
            // Type text into the content EditText
            onView(withId(R.id.contentEditText)).perform(typeText("Content test 3."))
            // Click the "Save" button
            onView(withId(R.id.saveButton)).perform(click())
        }
    }
}
