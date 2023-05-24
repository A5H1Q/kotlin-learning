package com.example.counterapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.counterapp.MainActivity.Companion.noteList
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var emptyNotesTxtView : TextView

    companion object {
        lateinit var noteList: MutableList<Note>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emptyNotesTxtView = findViewById(R.id.emptynotes)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        noteList = generateDummyNoteList()

        noteAdapter = NoteAdapter(noteList, launcher, emptyNotesTxtView)
        recyclerView.adapter = noteAdapter

        updateEmptyNotesVisibility()
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, NewNote::class.java)
            intent.putExtra("isNew", true)
            launcher.launch(intent)
        }
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        updateEmptyNotesVisibility()
        recyclerView.adapter?.notifyDataSetChanged()
    }
private fun generateDummyNoteList(): MutableList<Note> {
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
class NoteAdapter(private val notes: List<Note>,private val launcher: ActivityResultLauncher<Intent>, private val emptyNotesTxtView : TextView) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

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
    private fun deleteNote(noteToDelete: Note) {
        val position = noteList.indexOf(noteToDelete)
        if (position != -1) {
            noteList.removeAt(position)
            notifyItemRemoved(position)
            if (noteList.isEmpty()) {
                emptyNotesTxtView.visibility = View.VISIBLE
            } else {
                emptyNotesTxtView.visibility = View.GONE
            }
        }
    }
    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val previewTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val deleteIcon: ImageButton = itemView.findViewById(R.id.delBtn)
        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NewNote::class.java)
                intent.putExtra("isNew", false)
                intent.putExtra("noteId", notes[adapterPosition].id)
                launcher.launch(intent)
            }

            deleteIcon.setOnClickListener {
                deleteNote(notes[adapterPosition])
            }
        }
        fun bind(note: Note) {
            previewTextView.text = note.content.substring(0, minOf(note.content.length, 100))
        }
    }
}