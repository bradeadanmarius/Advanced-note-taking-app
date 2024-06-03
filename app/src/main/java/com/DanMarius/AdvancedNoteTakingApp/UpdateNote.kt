package com.DanMarius.AdvancedNoteTakingApp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.DanMarius.AdvancedNoteTakingApp.databinding.ActivityUpdateNoteBinding

// Activity for updating an existing note
class UpdateNote : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding // View binding for the activity
    private lateinit var db: DatabaseGeneral // Database helper for managing notes
    private var noteId: Int = -1 // ID of the note to be updated

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater) // Inflate layout using view binding
        setContentView(binding.root) // Set content view to the root of the inflated layout

        db = DatabaseGeneral(this) // Initialize database helper

        // Get note ID from the intent, default to -1 if not provided
        noteId = intent.getIntExtra("note_id", -1)
        // If note ID is invalid, finish the activity and return
        if (noteId == -1) {
            finish()
            return
        }

        // Retrieve the note to be updated from the database
        val note = db.getNoteByID(noteId)
        // Set the title and content of the note to the corresponding EditText fields
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

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
        importanceSpinner.setSelection(note.importance - 1)

        // Set click listener for the save button to update the note in the database
        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString() // Get updated title
            val newContent = binding.updateContentEditText.text.toString() // Get updated content
            val newImportance = importanceSpinner.selectedItemPosition + 1 // Get updated importance level
            val updatedNote = Note(noteId, newTitle, newContent, newImportance) // Create updated note object
            db.updateNote(updatedNote) // Update note in the database
            finish() // Finish the activity
            // Show a toast message indicating that changes are saved
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
