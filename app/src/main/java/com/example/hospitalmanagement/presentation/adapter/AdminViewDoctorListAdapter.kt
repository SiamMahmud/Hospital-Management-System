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

class AdminViewDoctorListAdapter(private var doctorList: ArrayList<DoctorInfo>) :
    RecyclerView.Adapter<AdminViewDoctorListAdapter.MyViewHolder>() {
    var onItemClick: ((DoctorInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.doctors_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    fun searchDataList(searchList: List<DoctorInfo>) {
        doctorList = ArrayList(searchList)
        notifyDataSetChanged()
    }
    
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = doctorList[position]

        holder.bind(currentItem)
        holder.card.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }

        holder.dName.text = currentItem.name
        holder.dDegree.text = currentItem.email
        holder.card.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dName: TextView
        val dDegree: TextView
        val card: CardView

        init {
            dName = itemView.findViewById(R.id.cardDocName)
            dDegree = itemView.findViewById(R.id.cardDocSpecial)
            card = itemView.findViewById(R.id.docCard)
        }

        fun bind(medicine: DoctorInfo) {
            dName.text = medicine.name
            dDegree.text = medicine.doctorSpecialization
        }


    }

}