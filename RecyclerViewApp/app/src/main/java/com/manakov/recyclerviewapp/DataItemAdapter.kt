package com.manakov.recyclerviewapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataItemAdapter : RecyclerView.Adapter<DataItemAdapter.DataItemViewHolder>() {
    lateinit var onClick: (dataItem: DataItem, position: Int) -> Unit
    lateinit var onLongClick: (position: Int) -> Unit

    var dataItemList = mutableListOf<DataItem>()
        set(value) {
            field.clear()
            value.forEach {
                field.add(it.copy())
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataItemViewHolder {
        return DataItemViewHolder(
            LayoutInflater
                .from(
                    parent.context
                )
                .inflate(
                    R.layout.item_data,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: DataItemViewHolder, position: Int) {
        val item = dataItemList[position]
        if (item.visibility) {
            holder.apply {
                itemView.apply {
                    visibility = View.VISIBLE
                    layoutParams = RecyclerView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }

                bind(
                    item.name,
                    item.surname,
                )
                itemView.setOnClickListener {
                    onClick(
                        dataItemList[position], position
                    )
                }
                itemView.setOnLongClickListener {
                    onLongClick(
                        position
                    )
                    return@setOnLongClickListener true
                }
            }
        } else {
            holder.itemView.apply {
                visibility = View.GONE
                layoutParams = RecyclerView.LayoutParams(0, 0)
            }
        }
    }

    override fun getItemCount() = dataItemList.size

    class DataItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.itemViewNameTextView)
        private val surnameTextView = itemView.findViewById<TextView>(R.id.itemViewSurnameTextView)

        fun bind(name: String, surname: String) {
            nameTextView.text = name
            surnameTextView.text = surname
        }
    }
}
