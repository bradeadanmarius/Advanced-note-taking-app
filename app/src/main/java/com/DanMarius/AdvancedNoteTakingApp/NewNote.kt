package com.DanMarius.AdvancedNoteTakingApp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.DanMarius.AdvancedNoteTakingApp.databinding.ActivityAddNoteBinding

// Activity for adding a new note
class NewNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding // View binding for the activity
    private lateinit var db: DatabaseGeneral // Database helper for managing notes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater) // Inflate layout using view binding
        setContentView(binding.root) // Set content view to the root of the inflated layout

        db = DatabaseGeneral(this) // Initialize database helper

        // Setup importance spinner
        val importanceSpinner = binding.importanceSpinner
        ArrayAdapter.createFromResource(
            this,
            R.array.importance_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            importanceSpinner.adapter = adapter
        }

        // Set click listener for the save button
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString() // Get title from the title edit text
            val content = binding.contentEditText.text.toString() // Get content from the content edit text
            val importance = importanceSpinner.selectedItemPosition + 1 // Get importance from spinner

            val note = Note(0, title, content, importance) // Create a new Note object with ID 0 and importance level

            db.insertNote(note) // Insert the new note into the database
            finish() // Finish the activity
            // Show a toast message indicating that the note is saved
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
