package com.example.uas_paba_klmpk6

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.budgeting

class adapterBudget (private val budgetData : MutableList<budgeting>) :RecyclerView.Adapter<adapterBudget.ListViewHolder>() {

    class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var _tvBudgetName = itemView.findViewById<TextView>(R.id.tvBudgetName)
        var _tvCurrentAmount = itemView.findViewById<TextView>(R.id.tvCurrentAmount)
        var _tvTargetAmount = itemView.findViewById<TextView>(R.id.tvTargetAmount)
        var _tvTimeRemaining = itemView.findViewById<TextView>(R.id.tvTimeRemaining)
        var _tvTargetDate = itemView.findViewById<TextView>(R.id.tvTargetDate)
        var _tvBudgetNote = itemView.findViewById<TextView>(R.id.tvBudgetNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}