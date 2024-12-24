package com.example.karamat_2024

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.karamat_2024.R
import org.json.JSONObject
import java.io.OutputStream
import java.io.InputStreamReader
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

class APICall_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apicall)

        // Get references to EditText, Button, and TextView
        val editTextInput = findViewById<EditText>(R.id.editTextInput)
        val buttonCallApi = findViewById<Button>(R.id.buttonSend)
        val textViewApiResponse = findViewById<TextView>(R.id.textViewApiResponse)

        textViewApiResponse.movementMethod = ScrollingMovementMethod()

        // Set the button's click listener
        buttonCallApi.setOnClickListener {
            val userInput = editTextInput.text.toString().trim()

            if (userInput.isNotBlank()) {
                // Call the API with the user input
                callApi(userInput, textViewApiResponse)
            } else {
                Toast.makeText(this, "Please enter a query", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to call the API
    private fun callApi(userInput: String, textViewApiResponse: TextView) {
        val apiUrl =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=AIzaSyAxLh6J6qb7Dv2i7MOQeyvMrqZLlCe-oas"

        // Prepare the JSON body with user input
        val jsonBody = """
            {
                "contents": [
                    {
                        "parts": [
                            {
                                "text": "$userInput"
                            }
                        ]
                    }
                ]
            }
        """

        // Start a background thread to perform the network request
        Thread {
            try {
                // Open connection to API
                val url = URL(apiUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true

                // Write the JSON body to the request
                val outputStream: OutputStream = connection.outputStream
                outputStream.write(jsonBody.toByteArray())
                outputStream.flush()

                // Read the API response
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()

                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }

                    reader.close()
                    val jsonResponse = JSONObject(response.toString())
                    val candidates = jsonResponse.getJSONArray("candidates")
                    val firstCandidate = candidates.getJSONObject(0)
                    val content = firstCandidate.getJSONObject("content")
                    val parts = content.getJSONArray("parts")
                    val extractedText = parts.getJSONObject(0).getString("text")
                    // Handle the response and update the UI
                    runOnUiThread {
                        // Display the response in the TextView like a chatbot
                        textViewApiResponse.text = "$extractedText"
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            "Request failed. Response Code: $responseCode",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }
}
