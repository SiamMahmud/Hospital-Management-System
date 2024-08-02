package com.example.hospitalmanagement.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.presentation.model.AppointmentInfo

class AppointmentAdapter (private val appointmentList: ArrayList<AppointmentInfo>) :
    RecyclerView.Adapter<AppointmentAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.appointment_list, parent, false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = appointmentList[position]
        holder.patientNTv.text = currentItem.patientN
        holder.aDateTv.text = currentItem.appointDate
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val patientNTv = itemView.findViewById<TextView>(R.id.patientNTv)
        val aDateTv = itemView.findViewById<TextView>(R.id.aDateTv)
    }
}