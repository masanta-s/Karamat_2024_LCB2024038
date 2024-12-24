package com.example.karamat_2024

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class Login_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        var buttonSubmit = findViewById<MaterialButton>(R.id.buttonSubmit)
        var editTextUserId = findViewById<EditText>(R.id.editTextUserId)
        var editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        buttonSubmit.setOnClickListener {
            // Get the user input from the EditText fields
            val userId = editTextUserId.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Validate the input fields
            if (userId.isEmpty()) {
                editTextUserId.error = "User ID is required"
                return@setOnClickListener
            } else {
//                editTextUserId.error = null
            }

            if (password.isEmpty()) {
                editTextPassword.error = "Password is required"
                return@setOnClickListener
            } else {
//                editTextPassword.error = null
            }

            var intent = Intent(this, APICall_Activity::class.java)
            startActivity(intent)
            // Optionally, show a success message or proceed with your logic
            Toast.makeText(this, "User ID: $userId\nPassword: $password", Toast.LENGTH_SHORT).show()
        }
    }
}