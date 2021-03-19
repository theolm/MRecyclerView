package com.theolm.testapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(private val list: ArrayList<String> = arrayListOf()) :
    RecyclerView.Adapter<MainAdapter.MainVH>() {

    fun addItems(items: List<String>) {
        if (list.size == 0) {
            list.addAll(items)
            notifyDataSetChanged()
        } else {
            val start = itemCount
            list.addAll(items)
            notifyItemRangeInserted(start, items.size)
        }
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainVH =
        MainVH(LayoutInflater.from(parent.context).inflate(R.layout.vh_main, parent, false))


    override fun onBindViewHolder(holder: MainVH, position: Int) {
        holder.bind("${list[position]} $position")
    }

    override fun getItemCount() = list.size

    inner class MainVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(title: String) {
            itemView.findViewById<TextView>(R.id.text).text = title
        }
    }

}