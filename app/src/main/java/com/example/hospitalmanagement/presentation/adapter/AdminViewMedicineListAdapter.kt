package com.example.hospitalmanagement.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.presentation.model.DoctorInfo
import com.example.hospitalmanagement.presentation.model.MedicineInfo

class AdminViewMedicineListAdapter (private val medicineList: ArrayList<MedicineInfo>) :
    RecyclerView.Adapter<AdminViewMedicineListAdapter.MyViewHolder>(){
    var onItemClick: ((MedicineInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.medicine_list, parent, false)
        return MyViewHolder(itemView) }

    override fun getItemCount(): Int {
       return medicineList.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = medicineList[position]
        holder.mName.text = currentItem.mediName
        holder.mPrice.text = currentItem.mediPrice
        holder.mCard.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mName = itemView.findViewById<TextView>(R.id.medicineNameTv)
        val mPrice = itemView.findViewById<TextView>(R.id.mPriceTv)
        val mCard = itemView.findViewById<CardView>(R.id.medicineCardLayout)
    }
}