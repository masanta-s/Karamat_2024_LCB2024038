package com.example.karamat_2024

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUp_Activity : AppCompatActivity() {

    private lateinit var editTextPassword1: EditText
    private lateinit var editTextPassword2: EditText
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        editTextPassword1 = findViewById(R.id.editTextPassword1_SignUp)
        editTextPassword2 = findViewById(R.id.editTextPassword2_SignUp)
        buttonSubmit = findViewById(R.id.buttonSubmit_SignUp)

        // Check password conditions dynamically as user types
        editTextPassword1.addTextChangedListener(passwordTextWatcher)
        editTextPassword2.addTextChangedListener(passwordTextWatcher)

        buttonSubmit.setOnClickListener {
            val password1 = editTextPassword1.text.toString().trim()
            val password2 = editTextPassword2.text.toString().trim()

            // Check if passwords match
            if (password1 != password2) {
                editTextPassword2.error = "Passwords do not match"
                return@setOnClickListener
            }

            // Check if all conditions are met for password1
            val conditionResults = checkPasswordConditions(password1)
            if (conditionResults.values.all { it }) {
                // All conditions are met, proceed to the APICall_Activity
                val intent = Intent(this, Login_Activity::class.java)
                startActivity(intent)
            } else {
                // Show error if conditions are not met
                val unmetConditions = conditionResults.filterNot { it.value }.keys.joinToString("\n") { "- $it" }
                editTextPassword1.error = "Password does not meet all conditions:\n$unmetConditions"
            }
        }
    }

    private val passwordTextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val password1 = editTextPassword1.text.toString().trim()

            // Get password conditions for password1
            val conditionResults = checkPasswordConditions(password1)

            // Create a string with conditions and their status (✅ or ❌)
            val conditionsWithStatus = conditionResults.entries.joinToString("\n") { (condition, isMet) ->
                val status = if (isMet) "✅" else "❌"
                "$status $condition"
            }

            // Display the error message with conditions and status for password1
            if (conditionsWithStatus.isNotEmpty()) {
                editTextPassword1.error = "Password conditions:\n$conditionsWithStatus"
            }

            // If all conditions are met, clear the error for password1
            if (conditionResults.values.all { it }) {
                editTextPassword1.error = null
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    /**
     * Checks password conditions and returns a map of conditions with their statuses.
     */
    private fun checkPasswordConditions(password: String): Map<String, Boolean> {
        return mapOf(
            "At least 8 characters" to (password.length >= 8),
            "Contains alphabet (a-z or A-Z)" to password.any { it.isLetter() },
            "Contains number (0-9)" to password.any { it.isDigit() },
            "Contains special character" to password.any { !it.isLetterOrDigit() }
        )
    }
}
