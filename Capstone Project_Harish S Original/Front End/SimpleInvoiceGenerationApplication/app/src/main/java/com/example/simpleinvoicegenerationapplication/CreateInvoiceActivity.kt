package com.example.simpleinvoicegenerationapplication

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CreateInvoiceActivity : AppCompatActivity() {

    private lateinit var etClientName: EditText
    private lateinit var etAmount: EditText
    private lateinit var etDate: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnSave: Button
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createinvoice)

        etClientName = findViewById(R.id.etClientName)
        etAmount = findViewById(R.id.etAmount)
        etDate = findViewById(R.id.etDate)
        etDescription = findViewById(R.id.etDescription)
        btnSave = findViewById(R.id.btnSave)

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService::class.java)

        btnSave.setOnClickListener {
            saveInvoice()
        }

        val calendarIcon = findViewById<ImageView>(R.id.calendarIcon)
        calendarIcon.setOnClickListener {
            showDatePicker()
        }
    }

    private fun saveInvoice() {
        val clientName = etClientName.text.toString().trim()
        val amount = etAmount.text.toString().trim()
        val date = etDate.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val userId = getUserIdFromSharedPreferences()

        if (clientName.isEmpty() || amount.isEmpty() || date.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Format the date string as ISO 8601 format (yyyy-MM-dd'T'HH:mm:ss)
        val formattedDate =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(Date())

        val invoice = Invoice(null, clientName, amount, formattedDate, description, userId)
        apiService.createInvoice(invoice, userId).enqueue(object : Callback<Invoice> {
            override fun onResponse(call: Call<Invoice>, response: Response<Invoice>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@CreateInvoiceActivity,
                        "Invoice created successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@CreateInvoiceActivity, DashboardActivity::class.java))
                    finish()
                } else  {
                    Toast.makeText(
                        this@CreateInvoiceActivity,
                        "Failed to create invoice",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Invoice>, t: Throwable) {
                Toast.makeText(
                    this@CreateInvoiceActivity,
                    "Network error!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun getUserIdFromSharedPreferences(): Long {
        val sharedPref = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        return sharedPref.getLong("USER_ID", 1)
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                // Format the date string as "yyyy-MM-dd"
                val formattedDate = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth)
                etDate.setText(formattedDate)
            },
            year,
            month,
            day
        )

        datePicker.datePicker.maxDate =
            System.currentTimeMillis() - 1000 // Restrict to present and past dates
        datePicker.show()
    }
}
