package com.example.simpleinvoicegenerationapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.Serializable

// User data class
data class User(

    val  id: Long,
    val userName: String,
    val password: String,
    val email: String,


    )

// Invoice class
data class Invoice(
    val id: Long? = null,
    val clientName: String,
    val amount: String,
    val date: String,
    val description: String,
    val userId: Long
) : Serializable





// API service interface

interface ApiService {

    @POST("/api/users/register")
    fun createUser(@Body user: User): Call<User>

    @POST("/api/users/login")
    fun loginUser(@Body credentials: User): Call<User>

    @POST("/createInvoice")
    fun createInvoice(@Body invoice: Invoice, @Query("userId") userId: Long): Call<Invoice>

    @GET("/getInvoices")
    fun getInvoices(@Query("userId") userId: Long): Call<List<Invoice>>

    @PUT("/updateInvoice")
    fun updateInvoice(@Body invoice:Invoice, @Query("userId") userId: Long): Call<Invoice>


    @GET("/getInvoiceById")
    fun getInvoiceById(@Query("id") id: Long): Call<Invoice>

    @DELETE("/invoiceDelete")
    fun deleteInvoice(@Query("id") invoiceId: Long): Call<Void>

}




// Retrofit client object
object RetrofitClient {
    private const val BASE_URL = "http://192.0.0.2:9000/api/" // Update the base URL
    private var retrofit: Retrofit? = null

    fun getRetrofitInstance(): Retrofit {
        if (retrofit == null) {
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}

// RetrofitActivity class
class RetrofitActivity : AppCompatActivity() {

    // Retrofit service instance
    private val apiService: ApiService by lazy {
        RetrofitClient.getRetrofitInstance().create(ApiService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of using the signup function
        signup()

        // Example of using the login function
        login()
    }

    private fun signup() {
        val user = User(-1,"userName", "password", "email")
        CoroutineScope(Dispatchers.IO).launch {
            if (user.email.isNotEmpty() && user.password.isNotEmpty()) {
                val call = apiService.createUser(user)
                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val createdUser = response.body()
                            // Handle successful user creation
                            Toast.makeText(this@RetrofitActivity, "User created successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            // Handle error response
                            Toast.makeText(this@RetrofitActivity, "Failed to create user", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        // Handle network errors
                        Toast.makeText(this@RetrofitActivity, "Network error", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@RetrofitActivity, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun login() {
        val credentials = User( -1,"username", "password", "")
        CoroutineScope(Dispatchers.IO).launch {
            if (credentials.userName.isNotEmpty() && credentials.password.isNotEmpty()) {
                val call = apiService.loginUser(credentials)
                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val loggedInUser = response.body()
                            // Handle successful login
                            Toast.makeText(this@RetrofitActivity, "Logged in successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            // Handle error response
                            Toast.makeText(this@RetrofitActivity, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        // Handle network errors
                        Toast.makeText(this@RetrofitActivity, "Network error", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@RetrofitActivity, "Username and password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }
}