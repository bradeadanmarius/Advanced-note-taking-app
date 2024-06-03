package com.DanMarius.AdvancedNoteTakingApp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterNotesHandler(
    private val notes: List<Note>,
    private val noteActionListener: NoteActionListener
) : RecyclerView.Adapter<AdapterNotesHandler.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        val importanceDrawable = when (note.importance) {
            1 -> R.drawable.importance_indicator_low
            2 -> R.drawable.importance_indicator_medium
            3 -> R.drawable.importance_indicator_high
            else -> R.drawable.importance_indicator_low
        }
        holder.importanceIndicator.setBackgroundResource(importanceDrawable)

        holder.updateButton.setOnClickListener {
            val intent = Intent(it.context, UpdateNote::class.java).apply {
                putExtra("note_id", note.id)
            }
            it.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            noteActionListener.onDeleteNote(note.id)
        }
    }

    override fun getItemCount() = notes.size

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val importanceIndicator: View = itemView.findViewById(R.id.importanceIndicator)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }
}
