package com.example.simpleinvoicegenerationapplication
import android.content.Intent
import com.example.simpleinvoicegenerationapplication.DashboardActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_signup)

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService::class.java)

        btnLogin.setOnClickListener {
            validateForm()
        }
    }

    private fun validateForm() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (username.isEmpty()) {
            etUsername.error = "Please enter your username"
            etUsername.requestFocus()
            return
        }

        if (password.isEmpty()) {
            etPassword.error = "Please enter your password"
            etPassword.requestFocus()
            return
        }

        // Validation successful, proceed with login
        login(User( -1 ,username, password, ""))
    }

    private fun login(user: User) {
        val call = apiService.loginUser(user)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful && response.body() != null) {
                    // User found, proceed to DashboardActivity
                    val userId = response.body()!!.id
                    saveUserIdToSharedPreferences(userId)
                    Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    startActivity(intent)
                    finish() // close the LoginActivity
                } else {
                    // User not found or invalid credentials
                    Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                // Network error
                Toast.makeText(this@LoginActivity, "Network error! Please try again.", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun saveUserIdToSharedPreferences(userId: Long) {
        val sharedPref = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putLong("USER_ID", userId)
            apply()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed() // Call the default behavior of the back button press
        val intent = Intent(this, SignUpActivity::class.java) // Create an Intent to start SignUpActivity
        startActivity(intent) // Start the SignUpActivity
        finish() // Finish LoginActivity to prevent navigating back to it
    }
}
