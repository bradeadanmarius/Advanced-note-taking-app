package com.DanMarius.AdvancedNoteTakingApp

import org.junit.Assert.assertEquals
import org.junit.Test

class NoteTest {

    @Test
    fun testNoteInstantiation() {
        val id = 1
        val title = "Test Note"
        val content = "This is a test note."
        val importance = 2 // Example importance level

        val note = Note(id, title, content, importance)

        assertEquals(id, note.id)
        assertEquals(title, note.title)
        assertEquals(content, note.content)
        assertEquals(importance, note.importance)
    }
}
