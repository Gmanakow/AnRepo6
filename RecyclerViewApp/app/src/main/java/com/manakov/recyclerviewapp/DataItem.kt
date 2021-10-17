package com.manakov.recyclerviewapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

const val ITEM_STRING = "ITEM_STRING"
const val INDEX_STRING = "INDEX_STRING"

@Parcelize
data class DataItem(
    var name : String = "",
    var surname : String = "",
    var number : String = "",
    var link : String = "",
    var visibility: Boolean = true
) : Parcelable
