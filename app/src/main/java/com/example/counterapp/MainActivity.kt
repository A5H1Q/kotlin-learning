package com.example.counterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private lateinit var counterTextView:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterTextView = findViewById(R.id.counter)
        val myFab:FloatingActionButton = findViewById(R.id.floatingActionButton)
        myFab.setOnClickListener {
            counter++
            counterTextView.text= getString(R.string.counter_text, counter)
        }
    }
}