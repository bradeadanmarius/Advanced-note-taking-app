package com.DanMarius.AdvancedNoteTakingApp

import android.content.Intent
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteUpdateTest {

    @Test
    fun updateNote_updatesSuccessfully() {
        // Assuming there's a note with ID 1 in the database
        val noteId = 1
        val intent = Intent(ApplicationProvider.getApplicationContext(), UpdateNote::class.java).apply {
            putExtra("note_id", noteId)
        }

        launch(NewNote::class.java).use {
            // Type text into the title EditText
            onView(withId(R.id.titleEditText)).perform(ViewActions.typeText(" Update Test Note 1"))
            // Type text into the content EditText
            onView(withId(R.id.contentEditText)).perform(ViewActions.typeText("Update test 1."))
            // Click the "Save" button
            onView(withId(R.id.saveButton)).perform(click())
        }

            // Launch UpdateNoteActivity with the prepared intent
        launch<UpdateNote>(intent).use {
            // Replace text in title and content EditTexts
            onView(withId(R.id.updateTitleEditText)).perform(replaceText("Updated Title"))
            onView(withId(R.id.updateContentEditText)).perform(replaceText("Updated content of the note."))
            // Click the "Save" button
            onView(withId(R.id.updateSaveButton)).perform(click())


        }
    }
}
