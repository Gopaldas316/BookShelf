package com.example.bookshelf.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bookshelf.R

class YearsAdapter(private val years: List<String>) : RecyclerView.Adapter<YearsAdapter.YearsViewHolder>() {

    inner class YearsViewHolder(view: View) : ViewHolder(view) {
        private val tvYear : TextView = view.findViewById(R.id.tvYear)
        private val vIsActive : View = view.findViewById(R.id.visActive)

        fun bind(item : String) {
            tvYear.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_year, parent, false)
        return YearsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return years.size
    }

    override fun onBindViewHolder(holder: YearsViewHolder, position: Int) {
        holder.bind(years[position])
    }
}