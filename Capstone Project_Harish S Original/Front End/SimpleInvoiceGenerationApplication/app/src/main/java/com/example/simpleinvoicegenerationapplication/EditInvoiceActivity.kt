package com.example.simpleinvoicegenerationapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditInvoiceActivity : AppCompatActivity() {

    private lateinit var etClientName: EditText
    private lateinit var etAmount: EditText
    private lateinit var etDate: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnSave: Button
    private lateinit var apiService: ApiService
    private lateinit var invoice: Invoice

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editinvoice)

        etClientName = findViewById(R.id.etClientName)
        etAmount = findViewById(R.id.etAmount)
        etDate = findViewById(R.id.etDate)
        etDescription = findViewById(R.id.etDescription)
        btnSave = findViewById(R.id.btnSave)

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService::class.java)

        val invoiceId = intent.getLongExtra("invoice_id", 0)

        if (invoiceId != -0L) {
            fetchInvoiceDetails(invoiceId)
        }

        btnSave.setOnClickListener {
            updateInvoice(invoiceId)
        }
    }

    private fun fetchInvoiceDetails(invoiceId: Long) {
        apiService.getInvoiceById(invoiceId).enqueue(object : Callback<Invoice> {
            override fun onResponse(call: Call<Invoice>, response: Response<Invoice>) {
                if (response.isSuccessful) {
                    invoice = response.body()!!
                    updateFields()
                } else {
                    Toast.makeText(
                        this@EditInvoiceActivity,
                        "Failed to fetch invoice details",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Invoice>, t: Throwable) {
                Toast.makeText(
                    this@EditInvoiceActivity,
                    "Network error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun updateInvoice(invoiceId: Long) {
        val clientName = etClientName.text.toString().trim()
        val amount = etAmount.text.toString().trim()
        val date = etDate.text.toString().trim()
        val description = etDescription.text.toString().trim()

        if (clientName.isEmpty() && amount.isEmpty() && date.isEmpty() && description.isEmpty()) {
            Toast.makeText(this, "Please fill at least one field to update", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val updatedInvoice = invoice.copy(
            clientName = clientName.takeIf { it.isNotEmpty() } ?: invoice.clientName,
            amount = amount.takeIf { it.isNotEmpty() } ?: invoice.amount,
            date = date.takeIf { it.isNotEmpty() } ?: invoice.date,
            description = description.takeIf { it.isNotEmpty() } ?: invoice.description,
            userId = getUserIdFromSharedPreferences()
        )

        apiService.updateInvoice(updatedInvoice,getUserIdFromSharedPreferences() ).enqueue(object : Callback<Invoice> {
            override fun onResponse(call: Call<Invoice>, response: Response<Invoice>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditInvoiceActivity,
                        "Invoice updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@EditInvoiceActivity,
                        "Failed to update invoice",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Invoice>, t: Throwable) {
                Toast.makeText(
                    this@EditInvoiceActivity,
                    "Network error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun updateFields() {
        etClientName.setText(invoice.clientName)
        etAmount.setText(invoice.amount)
        etDate.setText(invoice.date)
        etDescription.setText(invoice.description)
    }

    private fun getUserIdFromSharedPreferences(): Long {
        val sharedPref = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        return sharedPref.getLong("USER_ID", 1)
    }
}