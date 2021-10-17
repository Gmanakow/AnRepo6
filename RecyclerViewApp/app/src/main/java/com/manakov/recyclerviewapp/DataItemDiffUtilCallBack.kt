package com.manakov.recyclerviewapp

import androidx.recyclerview.widget.DiffUtil

class DataItemDiffUtilCallBack(
    private val oldList : List<DataItem>,
    private val newList : List<DataItem>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        (oldList[oldItemPosition].name == newList[newItemPosition].name) &&
        (oldList[oldItemPosition].surname == newList[newItemPosition].surname) &&
        (oldList[oldItemPosition].number == newList[newItemPosition].number) &&
        (oldList[oldItemPosition].link == newList[newItemPosition].link) &&
        (oldList[oldItemPosition].visibility == newList[newItemPosition].visibility)
}