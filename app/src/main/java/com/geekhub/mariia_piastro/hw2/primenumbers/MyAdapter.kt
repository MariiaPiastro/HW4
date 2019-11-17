package com.geekhub.mariia_piastro.hw2.primenumbers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class MyAdapter(private val primeNumber: ArrayList<Int>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return primeNumber.size
    }

    override fun onBindViewHolder(p0: ViewHolder, position: Int) {
        p0.bind(primeNumber[position])
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(primeNumbers: Int) {
            view.textView.text = primeNumbers.toString()
        }
    }
}