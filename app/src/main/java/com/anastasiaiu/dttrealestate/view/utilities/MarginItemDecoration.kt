package com.anastasiaiu.dttrealestate.view.utilities

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val topMargin: Int = 0,
    private val bottomMargin: Int = 0,
    private val rightMargin: Int = 0,
    private val leftMargin: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        with(outRect) {
            top = topMargin
            bottom = bottomMargin
            right = rightMargin
            left = leftMargin
        }
    }
}