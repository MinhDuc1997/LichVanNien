package com.example.root.lichvannien.modules

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.style.LineBackgroundSpan


class DrawLableForDate(private val radius: Float, private val color: Int, private val price: String) : LineBackgroundSpan {

    override fun drawBackground(canvas: Canvas, paint: Paint,
                                left: Int, right: Int, top: Int,
                                baseline: Int, bottom: Int,
                                charSequence: CharSequence,
                                start: Int, end: Int, lineNum: Int) {
        val oldColor = paint.getColor()
        val oldTextSize = paint.getTextSize()
        if (color != 0) {
            paint.setColor(color)
        }
        if (price != "") {
            paint.setTextSize(20f)
        }
        val text = price.toString()
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        val x = right / 2 + bounds.width()*2
        val y = bottom
        canvas.drawText(text, x.toFloat(), y.toFloat(), paint)
        paint.setTextSize(oldTextSize)
        paint.setColor(oldColor)
    }
}