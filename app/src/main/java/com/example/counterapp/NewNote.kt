package com.example.counterapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.example.counterapp.MainActivity.Companion.noteList

class NewNote : AppCompatActivity() {
    private lateinit var noteId: String
    private lateinit var multiTextView: EditText
    private lateinit var saveBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)
        val isNew: Boolean = intent.getBooleanExtra("isNew", true)

        supportActionBar?.title = if (isNew) "New Note" else "Edit Note"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        multiTextView = findViewById(R.id.multiTextView)
        saveBtn = findViewById(R.id.savebtn)

        // Add text change listener to the EditText
        multiTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEditText()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })


        // Run the validation initially
        validateEditText()

        noteId = intent.getStringExtra("noteId") ?: ""
        if(noteId != "") getMatchingText(noteId)
        saveBtn.setOnClickListener {
            if(isNew) {addNote(multiTextView.text.toString()) }else{ updateNote(noteId)}
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun validateEditText() {
        saveBtn.isEnabled = multiTextView.text?.trim()?.isNotBlank() == true
    }

    private fun addNote(txt:String){
        noteList.add(Note(generateRandomText(), txt))
        finish()
    }
    private fun updateNote(id: String){
        for ((i, note) in noteList.withIndex()) {
            if (note.id == id) {
                noteList[i] = Note(id,multiTextView.text.toString())
                break
            }
        }
        finish()
    }
    private fun getMatchingText(id: String){
        for (note in noteList) {
            if (note.id == id) {
                multiTextView.setText(note.content)
                break
            }
        }
    }
    private fun generateRandomText(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') // Define the allowed characters
        return (1..5)
            .map { allowedChars.random() } // Generate a random character from the allowed characters
            .joinToString("") // Join the characters into a string
    }

}