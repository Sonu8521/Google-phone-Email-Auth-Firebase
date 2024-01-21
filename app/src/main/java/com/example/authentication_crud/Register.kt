package com.example.authentication_crud

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Register: AppCompatActivity() {
    
    private lateinit var emailSignup:EditText
    private lateinit var passwordSignup:EditText
    private lateinit var registerButton: Button
    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()


        emailSignup = findViewById(R.id.emailSignup)
        passwordSignup = findViewById(R.id.passwordSignup)
        registerButton = findViewById(R.id.registerUserButton)
        mAuth = FirebaseAuth.getInstance()

        registerButton.setOnClickListener {
            val email = emailSignup.text.toString()
            val pass = passwordSignup.text.toString()
            if (emailSignup.text.isNotEmpty() && passwordSignup.text.isNotEmpty()) {
                signUp(email, pass)

                startActivity(Intent(this,dashboard::class.java))

                Toast.makeText(this, "Signed Up successfully", Toast.LENGTH_SHORT).show()
                
            } else {
                Toast.makeText(this, "Enter Email and Password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUp(email: String, pass: String) {

    }
}