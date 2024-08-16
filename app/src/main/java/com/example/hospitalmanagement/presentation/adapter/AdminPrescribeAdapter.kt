package com.example.hospitalmanagement.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.presentation.model.Prescription

class AdminPrescribeAdapter(private val prescriptionList: ArrayList<Prescription>) :
    RecyclerView.Adapter<AdminPrescribeAdapter.MyViewHolder>() {
    var onItemClick: ((Prescription) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.prescription_list, parent, false)
        return MyViewHolder (itemView)
    }

    override fun getItemCount(): Int {
        return prescriptionList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = prescriptionList[position]
        holder.patientNameTv.text = currentItem.pPatientName
        holder.doctorNTv.text = currentItem.pDocName
        holder.pCard.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val patientNameTv = itemView.findViewById<TextView>(R.id.patientNameTv)
        val doctorNTv = itemView.findViewById<TextView>(R.id.doctorNTv)
        val pCard = itemView.findViewById<CardView>(R.id.prescriptionCardLayout)

            }

}