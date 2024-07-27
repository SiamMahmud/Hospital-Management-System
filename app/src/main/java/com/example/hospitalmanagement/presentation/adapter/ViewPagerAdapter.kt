package com.example.hospitalmanagement.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalmanagement.R

class ViewPagerAdapter(private val views: List<View>) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPageViewHolder>() {
    class ViewPageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val container: FrameLayout = itemView.findViewById(R.id.view_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.login_landing_items, parent, false)
        return ViewPageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return views.size
    }

    override fun onBindViewHolder(holder: ViewPageViewHolder, position: Int) {
        holder.container.addView(views[position])
    }
}