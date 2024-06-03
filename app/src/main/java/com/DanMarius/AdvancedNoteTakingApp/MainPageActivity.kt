package com.DanMarius.AdvancedNoteTakingApp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.DanMarius.AdvancedNoteTakingApp.databinding.ActivityMainBinding

class MainPageActivity : AppCompatActivity(), NoteActionListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DatabaseGeneral
    private lateinit var adapterNotesHandler: AdapterNotesHandler
    private var noteList: List<Note> = listOf()
    private var filteredNoteList: MutableList<Note> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseGeneral(this)
        noteList = db.getAllNotes()
        filteredNoteList.addAll(noteList)

        adapterNotesHandler = AdapterNotesHandler(filteredNoteList, this)
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = adapterNotesHandler

        binding.addButton.setOnClickListener {
            val intent = Intent(this, NewNote::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterNotes(newText.orEmpty())
                return true
            }
        })

        try {
            val searchTextView = binding.searchView.findViewById<AutoCompleteTextView>(androidx.appcompat.R.id.search_src_text)
            searchTextView.setTextColor(Color.WHITE)
            searchTextView.setHintTextColor(Color.LTGRAY)
        } catch (e: Exception) {
            Log.e("MainPageActivity", "Error setting search view text colors", e)
        }
    }

    override fun onResume() {
        super.onResume()
        noteList = db.getAllNotes()
        filterNotes(binding.searchView.query.toString())
    }

    private fun filterNotes(query: String) {
        filteredNoteList.clear()
        if (query.isEmpty()) {
            filteredNoteList.addAll(noteList)
        } else {
            for (note in noteList) {
                if (note.title.contains(query, ignoreCase = true) || note.content.contains(query, ignoreCase = true)) {
                    filteredNoteList.add(note)
                }
            }
        }
        adapterNotesHandler.notifyDataSetChanged()
    }

    override fun onDeleteNote(noteId: Int) {
        db.deleteNote(noteId)
        onResume()
    }
}
