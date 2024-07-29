package com.example.hospitalmanagement.presentation.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.presentation.model.AmbulanceDetails
import com.example.hospitalmanagement.presentation.model.DoctorInfo

class AmbulanceListAdapter (private val ambulanceList: ArrayList<AmbulanceDetails>) :
    RecyclerView.Adapter<AmbulanceListAdapter.MyViewHolder>() {
    var onItemClick:((AmbulanceDetails) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ambulnace_list, parent, false)
        return MyViewHolder(itemView) }

    override fun getItemCount(): Int {
        return ambulanceList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = ambulanceList[position]
        holder.driverN.text = currentItem.driverName
        holder.dAmbulanceLocation.text = currentItem.ambulLocation
        holder.ambulanceCard.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val driverN = itemView.findViewById<TextView>(R.id.driverNameTv)
        val dAmbulanceLocation = itemView.findViewById<TextView>(R.id.ambulLocationTv)
        val ambulanceCard = itemView.findViewById<CardView>(R.id.ambulanceCardLayout)

    }
}