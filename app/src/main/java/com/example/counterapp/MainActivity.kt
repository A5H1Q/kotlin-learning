package com.example.counterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteList: List<Note>
    private lateinit var emptyNotesTxtView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emptyNotesTxtView = findViewById(R.id.emptynotes)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

//        noteList = mutableListOf<Note>()
        noteList = generateDummyNoteList()

        noteAdapter = NoteAdapter(noteList)
        recyclerView.adapter = noteAdapter

        updateEmptyNotesVisibility()
    }

    private fun generateDummyNoteList(): List<Note> {
        val notes = mutableListOf<Note>()
        // Add dummy notes to the list
        notes.add(Note("1", "This is my first note"))
        notes.add(Note("2", "Lorem ipsum dolor sit Amet adispicing se mi amor jedor sil vou play"))
        notes.add(Note("3", "Yeea ho"))
        return notes
    }

    private fun updateEmptyNotesVisibility(){
        if (noteList.isEmpty()) {
            emptyNotesTxtView.visibility = View.VISIBLE
        } else {
            emptyNotesTxtView.visibility = View.GONE
        }
    }
}
data class Note(val id: String, val content: String)
class NoteAdapter(private val notes: List<Note>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val previewTextView: TextView = itemView.findViewById(R.id.titleTextView)

        fun bind(note: Note) {
            previewTextView.text = note.content.substring(0, minOf(note.content.length, 100))
        }
    }
}