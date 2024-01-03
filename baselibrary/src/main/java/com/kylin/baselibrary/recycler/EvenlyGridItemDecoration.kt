package com.kylin.baselibrary.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * Created by kylin on 2024/1/3.
 * 均分的RecyclerView Grid 分割线
 */

class EvenlyGridItemDecoration(var space: Float, private var row: Int) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        super.getItemOffsets(outRect, view, parent, state)
        val pos = parent.getChildAdapterPosition(view)

        val min: Float = space / row
        val max = space - min

        if (pos % row == 0) {
            outRect.right = max.toInt()
        } else if ((pos + 1) % row == 0) {
            outRect.left = max.toInt()
        } else {
            var index = 1
            var oldLeft = 0
            var oldRight = max
            while (index <= (pos % row)) {
                val left = space - oldRight
                val right = max - left
                oldLeft = left.toInt()
                oldRight = right
                index++
            }
            outRect.left = oldLeft
            outRect.right = oldRight.toInt()
        }
    }

}