package com.eahc.consignmentnote.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eahc.consignmentnote.R
import com.eahc.consignmentnote.entities.ConnObj

class ConnectionListAdapter(private var list: List<ConnObj>): RecyclerView.Adapter<ConnectionListViewHolder>(){

    var selectedList: ArrayList<ConnObj> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConnectionListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_connlist, parent, false)
        return ConnectionListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConnectionListViewHolder, position: Int) {
        holder.profile.text = list[position].profile
        holder.checkBox.setOnCheckedChangeListener { _, b ->
            if(b){
                selectedList.add(list[position])
                notifyDataSetChanged()
            } else{
                selectedList.remove(list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class ConnectionListViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var selected: Boolean = false
    var profile: TextView = view.findViewById(R.id.profileLayout)
    var checkBox: CheckBox = view.findViewById(R.id.checkbox)
}