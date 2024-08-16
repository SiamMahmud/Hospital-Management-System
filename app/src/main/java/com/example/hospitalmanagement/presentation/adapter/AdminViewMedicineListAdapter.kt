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

class AdminViewMedicineListAdapter (private var medicineList: ArrayList<MedicineInfo>) :
    RecyclerView.Adapter<AdminViewMedicineListAdapter.MyViewHolder>(){
    var onItemClick: ((MedicineInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.medicine_list, parent, false)
        return MyViewHolder(itemView) }

    override fun getItemCount(): Int {
       return medicineList.size
    }
    fun searchDataList(searchList: List<MedicineInfo>) {
        medicineList = ArrayList(searchList)  
        notifyDataSetChanged()
    }
    
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = medicineList[position]
        holder.bind(currentItem)
        holder.mCard.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mName: TextView
        val mPrice: TextView
        val mCard: CardView

        init {
            mName = itemView.findViewById(R.id.medicineNameTv)
            mPrice = itemView.findViewById(R.id.mPriceTv)
            mCard = itemView.findViewById(R.id.medicineCardLayout)
        }

        fun bind(medicine: MedicineInfo) {
            mName.text = medicine.name
            mPrice.text = medicine.price
        }
    }
}