package com.eahc.consignmentnote.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.eahc.consignmentnote.R
import com.eahc.consignmentnote.entities.Consignment
import java.text.SimpleDateFormat
import java.util.*

class ConsignmentListAdapter(private var list: List<Consignment>):
    RecyclerView.Adapter<ConsignmentListViewHolder>(),
    Filterable {

    private lateinit var callback: EventListener
    private var consignmentFullList = list
    private var consignmentFilter = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsignmentListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_consigment, parent, false)
        return ConsignmentListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConsignmentListViewHolder, position: Int) {
        holder.consignmentNo.text = consignmentFilter[position].consignment
        val outputFormatter = SimpleDateFormat(" " +
                "dd MMM yyyy", Locale("es", "ES"))
        holder.date.text = outputFormatter.format(consignmentFilter[position].storedDate)

        holder.layConsignment.setOnClickListener {
            view ->
            callback.handleConsignment(consignmentFilter[position].consignment)
        }

        holder.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(it.context)
            builder.setMessage(it.context.getString(R.string.confirmDeleteConsignment, list[position].consignment))
                .setCancelable(false)
                .setPositiveButton(it.context.getString(R.string.yes)) { _, _ ->
                    callback.deleteConsignment(consignmentFilter[position].consignment)
                }
                .setNegativeButton(it.context.getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

    }

    override fun getItemCount(): Int {
        return consignmentFilter.size
    }

    interface EventListener {
        fun deleteConsignment(consignment: String)
        fun handleConsignment(consignment: String)
    }

    fun setEventListener(listener: EventListener) {
        callback = listener
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterList = ArrayList<Consignment>()
                if (constraint.toString().isEmpty()) {
                    filterList.addAll(consignmentFullList)
                } else {
                    for (row in consignmentFullList) {
                        if (row.consignment.contains(constraint.toString())
                        ) {
                            filterList.add(row)
                        }
                    }
                }

                val filtered = FilterResults()
                filtered.values = filterList
                return filtered
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                consignmentFilter = results?.values as ArrayList<Consignment>
                notifyDataSetChanged()
            }

        }
    }

}

class ConsignmentListViewHolder(view: View): RecyclerView.ViewHolder(view){
    var consignmentNo: TextView = view.findViewById(R.id.consignmentNo)
    var date: TextView = view.findViewById(R.id.consignmentDate)
    var layConsignment: LinearLayout = view.findViewById(R.id.layConsignment)
    var btnDelete: Button = view.findViewById(R.id.btnClear)
}