package com.example.uas_paba_klmpk6

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.database.expense
import com.example.uas_paba_klmpk6.database.history
import com.example.uas_paba_klmpk6.database.income
import java.text.NumberFormat
import java.util.Locale


class adapterAll (private val historyData : MutableList<history>)  : RecyclerView.Adapter<adapterAll.ListViewHolder> () {
    private lateinit var onItemClickCallback : OnItemClickCallback

    class OnItemClickCallback {

    }

    class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var tvAllType =itemView.findViewById<TextView>(R.id.incomeType)
        var tvAllTitle =itemView.findViewById<TextView>(R.id.incomeTittle)
        var tvAllDate =itemView.findViewById<TextView>(R.id.incomeDate)
        var tvAllMoney =itemView.findViewById<TextView>(R.id.incomeMoney)
        var tvAllItem =itemView.findViewById<ImageView>(R.id.iconType)
        var tvAllContainer = itemView.findViewById<ConstraintLayout>(R.id.incomeContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterAll.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(
            R.layout.income_item_list,parent,false
        )
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterAll.ListViewHolder, position: Int) {
        var history = historyData[position]
        holder.tvAllType.setText(history.category)
        holder.tvAllTitle.setText(history.title)
        holder.tvAllDate.setText(history.date)
        val rupiahFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        rupiahFormat.setMaximumFractionDigits(0) // Menghilangkan desimal
        val formattedAmount: String = rupiahFormat.format(history.amount)

        if(history.type == "income"){
            holder.tvAllMoney.setTextColor(Color.parseColor("#4BC355"))
            val moneyIncome = "+$formattedAmount"
            holder.tvAllMoney.text = moneyIncome
            holder.tvAllContainer.setBackgroundResource(R.drawable.green_border)

        }
        else{
            holder.tvAllMoney.setTextColor(Color.parseColor("#FF3728"))
            val moneyExpense = "-$formattedAmount"
            holder.tvAllMoney.text = moneyExpense
            holder.tvAllContainer.setBackgroundResource(R.drawable.red_border)
        }

        holder.tvAllContainer.setOnClickListener {
            val intent = Intent(it.context, DetailTransaction::class.java)
            intent.putExtra("title", history.title)
            intent.putExtra("amount", formattedAmount)
            intent.putExtra("date", history.date)
            intent.putExtra("category", history.category)
            intent.putExtra("note", history.note)
            intent.putExtra("type", history.type)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
      return historyData.size
    }
    fun isiData(history : List<history>){
        historyData.clear()
        historyData.addAll(history)
        notifyDataSetChanged()
    }
}