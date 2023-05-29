package com.example.counterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel:MainActivityViewModel
    private lateinit var txtView:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        txtView=findViewById(R.id.txtView)

        val btn = findViewById<Button>(R.id.button)

        btn.setOnClickListener {
//            Log.d("wtf",viewModel.str)
            CoroutineScope(Dispatchers.IO).launch {
                mockDownload()
            }
        }
    }

    private suspend fun mockDownload(){
        for (i in 1..1000){
            Log.d("wtf","Downloading usr: $i in ${Thread.currentThread().name}")
            withContext(Dispatchers.Main){
                txtView.text = getString(R.string.placeholder, i)
            }
            kotlinx.coroutines.delay(300)
        }
    }
}
