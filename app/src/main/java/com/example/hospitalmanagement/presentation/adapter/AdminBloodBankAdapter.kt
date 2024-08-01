package com.example.hospitalmanagement.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.presentation.model.BloodBankDetails

class AdminBloodBankAdapter(private val bloodBankList: ArrayList<BloodBankDetails>) :
    RecyclerView.Adapter<AdminBloodBankAdapter.MyViewHolder>() {
    var onItemClick: ((BloodBankDetails) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.blood_list, parent, false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return bloodBankList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = bloodBankList[position]
        holder.donorName.text = currentItem.donorName
        holder.bloodGroupType.text = currentItem.bloodGroupType
        holder.mCard.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val donorName = itemView.findViewById<TextView>(R.id.bDonorNameTv)
        val bloodGroupType = itemView.findViewById<TextView>(R.id.bloodGroupTv)
        val mCard = itemView.findViewById<CardView>(R.id.bloodCardLayout)

    }
}