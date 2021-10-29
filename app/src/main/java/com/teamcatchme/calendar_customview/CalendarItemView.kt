package com.teamcatchme.calendar_customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.teamcatchme.catchmesample.R

class CalendarItemView @JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val date: Int? = null,
    private val catchuList: Array<Int> = arrayOf(),
    private val isPrevious: Boolean = false
) :
    View(context, attrs, defStyleAttr) {
    private var paint: Paint = Paint()

    private fun drawDateRect(canvas: Canvas, date: Int) {
        paint.textSize = 36f
        if (isPrevious) paint.color = Color.GRAY
        canvas.drawText(
            date.toString(),
            (width / 2).toFloat(),
            ((height / 2) - ((paint.descent() + paint.ascent()) / 2)),
            paint
        )
    }

    private fun drawDateWithCatchuRect(canvas: Canvas, date: Int, catchuList: Array<Int>) {
        paint.textSize = 30f
        paint.color = Color.GRAY
        val contextResources = context.resources
        val catchuDrawable =
            ResourcesCompat.getDrawable(contextResources, R.drawable.ic_cachu1, null);
        val catchuBitmap = drawableToBitmap(requireNotNull(catchuDrawable))
        runCatching {
            canvas.drawBitmap(
                requireNotNull(catchuBitmap),
                (width / 2 - catchuBitmap.width / 2).toFloat(),
                0f,
                null
            )
            canvas.drawText(
                date.toString(),
                (width / 2).toFloat(),
                (catchuBitmap.height + 28).toFloat(),
                paint
            )
        }.onFailure { Log.e("error", it.toString()) }

        // 누르면 프라그먼트 뿅 하게 리스너 추가하기
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.textAlign = Paint.Align.CENTER
        if (canvas == null) return;
        date?.run {
            if (catchuList.isNotEmpty()) drawDateWithCatchuRect(canvas, this, catchuList)
            else drawDateRect(canvas, this)
        }
    }
}