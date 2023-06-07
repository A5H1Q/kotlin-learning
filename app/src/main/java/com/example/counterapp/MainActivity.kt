package com.example.counterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var counterTextView: TextView
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterTextView = findViewById(R.id.counter)

        val exFab: ExtendedFloatingActionButton = findViewById(R.id.fab)
        exFab.setOnClickListener {
            mainScope.launch {
                val response = withContext(Dispatchers.IO) {
                    sendGetRequest("https://www.thiswebsitewillselfdestruct.com/api/get_letter") // Replace with your URL
                }
                counterTextView.text = response
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel() // Cancel the coroutine when the activity is destroyed
    }

    private fun sendGetRequest(urlString: String): String {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = StringBuilder()

            var line: String? = reader.readLine()
            while (line != null) {
                response.append(line)
                line = reader.readLine()
            }

            reader.close()
            return response.toString()
        } else {
            throw Exception("HTTP GET request failed with response code: $responseCode")
        }
    }
}
