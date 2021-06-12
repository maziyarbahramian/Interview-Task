package com.maziyar.interview.utils

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class SpaceItemDecoration(context: Context, margin: Float) : RecyclerView.ItemDecoration() {

    private var space: Int

    init {
        val metrics = context.resources.displayMetrics
        space = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, metrics).toInt()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
//        outRect.left = space
//        outRect.right = space
        outRect.bottom = space / 2
        outRect.top = space / 2

//        if (parent.getChildLayoutPosition(view) == 0) {
//            outRect.top = space
//        } else {
//            outRect.top = 0;
//        }
    }
}