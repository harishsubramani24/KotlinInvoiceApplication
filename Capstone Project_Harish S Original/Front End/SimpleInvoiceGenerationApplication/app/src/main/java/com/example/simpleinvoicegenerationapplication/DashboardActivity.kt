package com.example.simpleinvoicegenerationapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity(), InvoiceAdapter.OnItemClickListener, InvoiceAdapter.OnEditClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InvoiceAdapter
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService::class.java)

        adapter = InvoiceAdapter(emptyList(), this, this)
        recyclerView.adapter = adapter

        val addInvoiceButton: ImageView = findViewById(R.id.addInvoiceButton)
        addInvoiceButton.setOnClickListener {
            val intent = Intent(this, CreateInvoiceActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchInvoices(getUserIdFromSharedPreferences())
    }

    private fun fetchInvoices(userId: Long) {
        apiService.getInvoices(userId).enqueue(object : Callback<List<Invoice>> {
            override fun onResponse(call: Call<List<Invoice>>, response: Response<List<Invoice>>) {
                if (response.isSuccessful) {
                    val invoices = response.body() ?: emptyList()
                    adapter = InvoiceAdapter(invoices, this@DashboardActivity, this@DashboardActivity)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@DashboardActivity, "No Invoices Found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Invoice>>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClick(invoice: Invoice) {
        val intent = Intent(this, InvoiceDetailsActivity::class.java)
        intent.putExtra("invoice", invoice)
        startActivity(intent)
    }

    override fun onEditClick(invoice: Invoice) {
        val intent = Intent(this, EditInvoiceActivity::class.java)
        intent.putExtra("invoice_id", invoice.id)
        startActivity(intent)
    }

    private fun getUserIdFromSharedPreferences(): Long {
        val sharedPref = getSharedPreferences("USER_PREF", MODE_PRIVATE)
        return sharedPref.getLong("USER_ID", 1)
    }
}
