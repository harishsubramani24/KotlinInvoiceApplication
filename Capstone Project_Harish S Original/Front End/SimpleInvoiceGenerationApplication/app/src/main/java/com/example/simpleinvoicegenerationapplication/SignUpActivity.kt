package com.example.simpleinvoicegenerationapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.simpleinvoicegenerationapplication.RetrofitClient.getRetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etEmailSignUp: EditText
    private lateinit var etPasswordSignUp: EditText
    private lateinit var btnSignUp: Button
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFullName = findViewById(R.id.etFullName)
        etEmailSignUp = findViewById(R.id.etEmailSignUp)
        etPasswordSignUp = findViewById(R.id.etPasswordSignUp)
        btnSignUp = findViewById(R.id.btnSignUp)
        btnLogin = findViewById(R.id.btnLogin)

        btnSignUp.setOnClickListener {
            signUp()
        }

        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signUp() {
        val fullName = etFullName.text.toString().trim()
        val email = etEmailSignUp.text.toString().trim()
        val password = etPasswordSignUp.text.toString().trim()

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password should be at least 6 characters long", Toast.LENGTH_SHORT).show()
            return
        }

        val newUser = User(-1,fullName, password, email)
        val apiService = getRetrofitInstance().create(ApiService::class.java)
        val createUserCall = apiService.createUser(newUser)
        createUserCall.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SignUpActivity, "User created successfully!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                    finish()
                } else {
                    if (response.code() == 500) {
                        Toast.makeText(this@SignUpActivity, "Account already exists!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@SignUpActivity, "User creation failed!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, "Network Error!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}