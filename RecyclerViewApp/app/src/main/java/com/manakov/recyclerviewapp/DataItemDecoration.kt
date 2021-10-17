package com.manakov.recyclerviewapp

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

const val OFFSET = 40

class DataItemDecoration : RecyclerView.ItemDecoration() {
 // отступы через ItemDecoration
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (view.visibility == View.VISIBLE) {
            outRect.bottom = OFFSET
            outRect.left = OFFSET
            outRect.right = OFFSET
        }
    }
}