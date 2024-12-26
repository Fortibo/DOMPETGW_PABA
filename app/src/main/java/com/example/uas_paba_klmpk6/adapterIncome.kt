package com.example.uas_paba_klmpk6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.income

class adapterIncome(private val incomeData : MutableList<income>) : RecyclerView.Adapter<adapterIncome.ListViewHolder> () {
    private lateinit var onItemClickCallback : OnItemClickCallback

    class OnItemClickCallback {

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterIncome.ListViewHolder {
       val view : View = LayoutInflater.from(parent.context).inflate(
           R.layout.income_item_list,parent,false
       )
        return ListViewHolder(view)
    }

    class ListViewHolder (itemView: View):RecyclerView.ViewHolder(itemView)  {
        var tvIncomeType =itemView.findViewById<TextView>(R.id.incomeType)
        var tvIncomeTitle =itemView.findViewById<TextView>(R.id.incomeTittle)
        var tvIncomeDate =itemView.findViewById<TextView>(R.id.incomeDate)
        var tvIncomeMoney =itemView.findViewById<TextView>(R.id.incomeMoney)
        var tvIncomeItem =itemView.findViewById<ImageView>(R.id.iconType)
        var tvIncomeContainer = itemView.findViewById<ConstraintLayout>(R.id.incomeContainer)
    }

    override fun onBindViewHolder(holder: adapterIncome.ListViewHolder, position: Int) {
        var income = incomeData[position]
        holder.tvIncomeType.setText(income.category)
        holder.tvIncomeTitle.setText(income.title)
        holder.tvIncomeDate.setText(income.date)
        holder.tvIncomeMoney.setText(income.amount)


    }

    override fun getItemCount(): Int {
      return incomeData.size
    }
    fun isiData(income : List<income>){
        incomeData.clear()
        incomeData.addAll(income)
        notifyDataSetChanged()
    }
}

