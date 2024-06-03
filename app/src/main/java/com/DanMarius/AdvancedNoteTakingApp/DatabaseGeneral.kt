package com.DanMarius.AdvancedNoteTakingApp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Define a helper class for managing notes in SQLite database
class DatabaseGeneral(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Companion object to hold constants related to database
    companion object {
        private const val DATABASE_NAME = "notesapp.db" // Database name
        private const val DATABASE_VERSION = 1 // Database version
        private const val TABLE_NAME = "allnotes" // Table name
        private const val COLUMN_ID = "id" // Column for note ID
        private const val COLUMN_TITLE = "title" // Column for note title
        private const val COLUMN_CONTENT = "content" // Column for note content
        private const val COLUMN_IMPORTANCE = "importance" // Column for note importance
    }

    // Called when the database is created for the first time
    override fun onCreate(db: SQLiteDatabase?) {
        // SQL query to create the table for storing notes
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT, $COLUMN_IMPORTANCE INTEGER)"
        // Execute the SQL query
        db?.execSQL(createTableQuery)
    }

    // Called when the database needs to be upgraded
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // SQL query to drop the existing table
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        // Execute the SQL query
        db?.execSQL(dropTableQuery)
        // Recreate the table
        onCreate(db)
    }

    // Method to insert a new note into the database
    fun insertNote(note: Note) {
        val db = writableDatabase
        // Prepare values to be inserted into the database
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_IMPORTANCE, note.importance) // Add this line
        }
        // Insert the values into the database
        db.insert(TABLE_NAME, null, values)
        db.close() // Close the database connection
    }

    // Method to retrieve all notes from the database
    fun getAllNotes(): List<Note> {
        val noteList = mutableListOf<Note>()
        val db = readableDatabase
        // SQL query to select all notes from the table
        val query = "SELECT * FROM $TABLE_NAME"
        // Execute the query and get a cursor to iterate through the results
        val cursor = db.rawQuery(query, null)

        // Iterate through the cursor to retrieve each note
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val importance = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMPORTANCE)) // Add this line

            val note = Note(id, title, content, importance) // Add this line
            noteList.add(note) // Add note to the list
        }
        cursor.close() // Close the cursor
        db.close() // Close the database connection
        return noteList // Return the list of notes
    }

    // Method to update an existing note in the database
    fun updateNote(note: Note) {
        val db = writableDatabase
        // Prepare values to be updated in the database
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_IMPORTANCE, note.importance) // Add this line
        }
        // Define the WHERE clause for the update operation
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString()) // Arguments for the WHERE clause
        // Update the note in the database
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close() // Close the database connection
    }

    // Method to retrieve a note from the database by its ID
    fun getNoteByID(noteId: Int): Note {
        val db = readableDatabase
        // SQL query to select a note by its ID
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        // Execute the query and get a cursor to retrieve the result
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst() // Move the cursor to the first row

        // Retrieve note details from the cursor
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
        val importance = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMPORTANCE)) // Add this line

        cursor.close() // Close the cursor
        db.close() // Close the database connection
        return Note(id, title, content, importance) // Add this line
    }

    // Method to delete a note from the database by its ID
    fun deleteNote(noteId: Int) {
        val db = writableDatabase
        // Define the WHERE clause for the delete operation
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString()) // Arguments for the WHERE clause
        // Delete the note from the database
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close() // Close the database connection
    }
}
