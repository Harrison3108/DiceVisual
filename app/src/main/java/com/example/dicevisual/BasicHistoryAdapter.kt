package com.example.dicevisual

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BasicHistoryAdapter(private val lHistory: List<History>): RecyclerView.Adapter<BasicHistoryAdapter.ViewHolder>() {

    //Get IDs for elements in history_item_simple.xml
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val inputShown = itemView.findViewById<TextView>(R.id.txtid_input)
        val resultShown = itemView.findViewById<TextView>(R.id.txtid_result)
        val countShown = itemView.findViewById<TextView>(R.id.txtid_HistoryNum)
    }

    //Allow the RecyclerView to be used with basic history items (history_item_simple.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val historyView = inflater.inflate(R.layout.history_item_simple, parent, false)
        return ViewHolder(historyView)
    }

    //Get Size of History Items Displayed
    override fun getItemCount(): Int {
        return lHistory.size
    }

    //Visually update recyclerview item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Get History List
        val historyItem: History = lHistory.get(position)

        //----------------------------------------------
        //Update visually the history item

        //Result from Calculation (Output)
        val tvResult = holder.resultShown
        tvResult.text = historyItem.result

        //Input to create result (Input)
        val tvInput = holder.inputShown
        tvInput.text = historyItem.input

        //History Item (For Ease of Use)
        val tvCount = holder.countShown
        tvCount.text = historyItem.count.toString()
    }


}