package com.example.simpleinvoicegenerationapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvoiceDetailsActivity : AppCompatActivity() {

    private lateinit var etClientName: EditText
    private lateinit var etAmount: EditText
    private lateinit var etDate: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnSave: Button
    private var invoice: Invoice? = null // Declaring as nullable
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailsinvoice)

        // Initialize views
        etClientName = findViewById(R.id.etClientName)
        etAmount = findViewById(R.id.etAmount)
        etDate = findViewById(R.id.etDate)
        etDescription = findViewById(R.id.etDescription)
        btnSave = findViewById(R.id.btnSave)

        // Get invoice details from intent
        invoice = intent.getSerializableExtra("invoice") as? Invoice

        if (invoice != null) {
            // Populate views with invoice details
            etClientName.setText(invoice!!.clientName ?: "")
            etAmount.setText(invoice!!.amount?.toString() ?: "")
            etDate.setText(invoice!!.date ?: "")
            etDescription.setText(invoice!!.description ?: "")
        } else {
            // Handle case where invoice is null
            Toast.makeText(this, "Error: Invoice details not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Initialize ApiService
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService::class.java)

        // Save button click listener
        btnSave.setOnClickListener {
            // Handle delete button click
            showDeleteConfirmationDialog()
        }
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this invoice?")
        builder.setPositiveButton("Yes") { dialog, which ->
            // Perform delete operation here
            deleteInvoice()
        }
        builder.setNegativeButton("No") { dialog, which ->
            // Dismiss dialog
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun deleteInvoice() {
        val invoiceId = invoice?.id ?: return  // Ensure invoice is not null and get its ID
        apiService.deleteInvoice(invoiceId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@InvoiceDetailsActivity, "Invoice deleted successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@InvoiceDetailsActivity, "Failed to delete invoice", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@InvoiceDetailsActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
