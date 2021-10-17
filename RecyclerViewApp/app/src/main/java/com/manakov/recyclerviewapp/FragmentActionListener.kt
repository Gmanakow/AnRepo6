package com.manakov.recyclerviewapp

interface FragmentActionListener {
    fun onSelectItemAction(dataItem: DataItem, index: Int)
    fun onSaveStateAction(dataItem: DataItem, index : Int)
    fun onChangeSearchStatus(boolean: Boolean)
}