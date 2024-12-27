package com.example.uas_paba_klmpk6

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uas_paba_klmpk6.adapterAll.ListViewHolder
import com.example.uas_paba_klmpk6.database.history
import java.text.NumberFormat
import java.util.Locale

class adapterMutasiAll (private val historyData : MutableList<history>): RecyclerView.Adapter<adapterMutasiAll.ListViewHolder> () {

    class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var tvAllTglMutasi =itemView.findViewById<TextView>(R.id.rvTanggalMutasi)
        var tvAllTglHariMutasi =itemView.findViewById<TextView>(R.id.rvTanggalHariMutasi)
        var tvAllIdMutasi =itemView.findViewById<TextView>(R.id.rvIdMutasi)
        var tvAllTipeTransaksiMutasi =itemView.findViewById<TextView>(R.id.rvTipeTransaksiMutasi)
        var tvAllsetDBCB =itemView.findViewById<TextView>(R.id.setDBCB)
        var tvAllMoneyMutasi =itemView.findViewById<TextView>(R.id.rvMoneyMutasi)
        var tvAllJudulMutasi =itemView.findViewById<TextView>(R.id.rvJudulMutasi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterMutasiAll.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_mutation,parent,false
        )
        return adapterMutasiAll.ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var history = historyData[position]

        val rupiahFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        rupiahFormat.setMaximumFractionDigits(0) // Menghilangkan desimal
        val formattedAmount: String = rupiahFormat.format(history.amount)

        holder.tvAllTglMutasi.setText(history.date)
        holder.tvAllTglHariMutasi.setText(history.date)
        holder.tvAllIdMutasi.setText(position.toString())
        holder.tvAllMoneyMutasi.setText(formattedAmount)
        holder.tvAllTipeTransaksiMutasi.setText(history.type)
        holder.tvAllJudulMutasi.setText(history.title)
        if(history.type == "income"){
            holder.tvAllsetDBCB.setTextColor(Color.parseColor("#4BC355"))
            holder.tvAllsetDBCB.setText("CR")
            holder.tvAllMoneyMutasi.setTextColor(Color.parseColor("#4BC355"))
        }
        else{
            holder.tvAllsetDBCB.setTextColor(Color.parseColor("#FF3728"))
            holder.tvAllsetDBCB.setText("DB")
            holder.tvAllMoneyMutasi.setTextColor(Color.parseColor("#FF3728"))
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