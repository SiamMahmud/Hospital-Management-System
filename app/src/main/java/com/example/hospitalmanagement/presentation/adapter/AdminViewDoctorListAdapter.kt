package com.example.hospitalmanagement.presentation.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmanagement.R
import com.example.hospitalmanagement.presentation.model.DoctorInfo

class AdminViewDoctorListAdapter(private val doctorList: ArrayList<DoctorInfo>) :
    RecyclerView.Adapter<AdminViewDoctorListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.doctors_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = doctorList[position]
        holder.dName.text = currentItem.name
        holder.dDegree.text = currentItem.specialty
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dName = itemView.findViewById<TextView>(R.id.cardDocName)
        val dDegree = itemView.findViewById<TextView>(R.id.cardDocSpecial)
    }

}