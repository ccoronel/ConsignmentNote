package com.eahc.consignmentnote.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eahc.consignmentnote.R
import com.eahc.consignmentnote.entities.ConsignmentWS
import java.text.SimpleDateFormat
import java.util.*

class AddConsignmentListAdapter(private val list: List<ConsignmentWS>):
    RecyclerView.Adapter<AddConsignmentViewHolder>(),
    Filterable {

    val outputFormatter = SimpleDateFormat(" " +
            "dd MMM yyyy", Locale("es", "ES"))
    private var consignmentFullList = list
    private var consignmentFilter = list
    var selectedList: ArrayList<ConsignmentWS> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddConsignmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_add_consignment_list, parent, false)
        return AddConsignmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddConsignmentViewHolder, position: Int) {
        val consignmentWS = consignmentFilter[position]
        holder.consignment.text = consignmentWS.consignment
        holder.date.text = outputFormatter.format(consignmentWS.storedDate)

        if(consignmentWS.selected){
            holder.imageView.setImageResource(R.drawable.ic_baseline_check_circle_24)
            if(!selectedList.contains(consignmentWS)) {
                selectedList.add(consignmentWS)
            }
        } else {
            holder.imageView.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
            if(selectedList.contains(consignmentWS)) {
                selectedList.remove(consignmentWS)
            }
        }

        holder.itemView.setOnClickListener {
            if (consignmentWS.selected) {
                if(selectedList.contains(consignmentWS)) {
                    selectedList.remove(consignmentWS)
                }
                consignmentWS.selected = false
                list.find { dept -> dept == consignmentWS }?.selected = false
                holder.imageView.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
            } else {
                consignmentWS.selected = true
                list.find { dept -> dept == consignmentWS }?.selected = true
                holder.imageView.setImageResource(R.drawable.ic_baseline_check_circle_24)
                if(!selectedList.contains(consignmentWS)) {
                    selectedList.add(consignmentWS)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return consignmentFilter.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterList = ArrayList<ConsignmentWS>()
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
                consignmentFilter = results?.values as ArrayList<ConsignmentWS>
                notifyDataSetChanged()
            }

        }
    }
}

class AddConsignmentViewHolder(view: View): RecyclerView.ViewHolder(view){
    val consignment = view.findViewById<TextView>(R.id.consignmentNo)
    val date = view.findViewById<TextView>(R.id.consignmentDate)
    var imageView: ImageView = view.findViewById(R.id.imageView)
}