package com.example.studentdata



import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ItemSpacingDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {

    private val paint = Paint()

    init {
        paint.style = Paint.Style.FILL
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = spaceHeight
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val backgroundColor = getBackgroundColor(parent)

        paint.color = backgroundColor

        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val top = child.bottom // Start drawing from the bottom of the current child
            val bottom = top + spaceHeight // Set the bottom position based on the spaceHeight
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }



    private fun getBackgroundColor(parent: RecyclerView): Int {
        val drawable = parent.background ?: return ContextCompat.getColor(parent.context, android.R.color.transparent)
        val background = drawable.constantState?.newDrawable()?.mutate() ?: return ContextCompat.getColor(parent.context, android.R.color.transparent)
        val bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        background.setBounds(0, 0, canvas.width, canvas.height)
        background.draw(canvas)
        return bitmap.getPixel(0, 0)
    }
}
