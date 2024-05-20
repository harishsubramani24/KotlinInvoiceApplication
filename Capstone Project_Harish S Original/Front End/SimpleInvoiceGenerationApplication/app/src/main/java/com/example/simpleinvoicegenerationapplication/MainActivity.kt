package com.example.simpleinvoicegenerationapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.simpleinvoicegenerationapplication.R.id.btnSignUp

class MainActivity : AppCompatActivity() {

    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the sign-up button
        btnSignUp = findViewById(R.id.btnSignUp)

        // Set click listener for the sign-up button
        btnSignUp.setOnClickListener {
            // Navigate to SignUpActivity when sign-up button is clicked
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
