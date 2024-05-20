package com.example.simpleinvoicegenerationapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InvoiceAdapter(
    private val invoices: List<Invoice>,
    private val itemClickListener: OnItemClickListener,
    private val editClickListener: OnEditClickListener
) : RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(invoice: Invoice)
    }

    interface OnEditClickListener {
        fun onEditClick(invoice: Invoice)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val clientName: TextView = itemView.findViewById(R.id.clientName)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val date: TextView = itemView.findViewById(R.id.date)
        val description: TextView = itemView.findViewById(R.id.description)
        val editButton: ImageButton = itemView.findViewById(R.id.editButton)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(invoices[position])
                }
            }
            editButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    editClickListener.onEditClick(invoices[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_invoice, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val invoice = invoices[position]
        holder.clientName.text = invoice.clientName
        holder.amount.text = invoice.amount.toString()
        holder.date.text = invoice.date
        holder.description.text = invoice.description
    }

    override fun getItemCount(): Int {
        return invoices.size
    }
}
