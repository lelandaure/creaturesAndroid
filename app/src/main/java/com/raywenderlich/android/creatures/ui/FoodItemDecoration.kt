package com.raywenderlich.android.creatures.ui

import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class FoodItemDecoration(
    color: Int,
    private val dividerWidthInPixels: Int,
) : RecyclerView.ItemDecoration() {

    private val paint = Paint()

    init {
        paint.color = color
        paint.isAntiAlias = true
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            var left = parent.paddingLeft
/*width*/   var right = parent.width - parent.paddingRight
            var top = child.top + params.topMargin
/*height*/  var bottom = top + dividerWidthInPixels
            c.drawRect(left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                paint)
            left = child.right - params.rightMargin
/*width*/   right = left + dividerWidthInPixels
            top = parent.paddingTop
/*height*/  bottom = parent.height - parent.paddingBottom

            c.drawRect(left.toFloat(),
                top.toFloat(),
                right.toFloat(),
                bottom.toFloat(),
                paint)
        }
    }
}