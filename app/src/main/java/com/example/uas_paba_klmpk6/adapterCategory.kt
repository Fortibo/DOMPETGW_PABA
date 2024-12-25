package com.example.uas_paba_klmpk6

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.category

class adapterCategory(private val category: MutableList<category>):RecyclerView
.Adapter<adapterCategory.ListViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterCategory.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.income_category_layout, parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterCategory.ListViewHolder, position: Int) {
        var daftar = category[position]

        holder._namaCategory.setText(daftar.name)
        holder._btn.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return category.size
    }

    inner class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var _btn = itemView.findViewById<ConstraintLayout>(R.id.categoryBtn)
        var _namaCategory = itemView.findViewById<TextView>(R.id.categoryName)
    }

}