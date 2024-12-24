package com.example.karamat_2024

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        var btnlogin =findViewById<MaterialButton>(R.id.btn_login)
        btnlogin.setOnClickListener{
            Toast.makeText(this, "Login Button is Clicked !!!", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, Login_Activity::class.java)
            startActivity(intent)
        }
        var btnsignup = findViewById<MaterialButton>(R.id.btn_signup)
        btnsignup.setOnClickListener{
            Toast.makeText(this, "Sign Up Button is Clicked !!!", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, SignUp_Activity::class.java)
            startActivity(intent)
        }
    }
}