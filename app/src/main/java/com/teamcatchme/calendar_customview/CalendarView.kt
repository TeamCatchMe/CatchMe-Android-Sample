package com.teamcatchme.calendar_customview

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.children
import java.util.*

class CalendarView @JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    fun drawCalendar(dateObject: Date) {
        val dateCalendar = Calendar.getInstance()
        dateCalendar.time = dateObject
        dateCalendar.set(Calendar.DATE, 1)
        val year = dateCalendar.get(Calendar.YEAR)
        val month = dateCalendar.get(Calendar.MONTH) + 1
        val firstDayOfMonth = dateCalendar.get(Calendar.DAY_OF_WEEK) - 1
        val lastDateOfMonth = dateCalendar.getActualMaximum(Calendar.DATE)
        dateCalendar.set(Calendar.MONTH, month - 2)
        val lastDateOfLastMonth = dateCalendar.getActualMaximum(Calendar.DATE)
        if (firstDayOfMonth != 6) {
            for (blank in (lastDateOfLastMonth - firstDayOfMonth)..lastDateOfLastMonth) {
                addView(CalendarItemView(context = context, date = blank, isPrevious = true))
            }
        }
        for (date in 1..lastDateOfMonth) {
            if (date % 2 == 0) addView(
                CalendarItemView(
                    context = context,
                    date = date,
                    catchuList = arrayOf(1, 2, 3)
                )
            )
            else addView(CalendarItemView(context = context, date = date))
        }
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        val iWidth = (width / 7).toFloat()
        val iHeight = (width / 7).toFloat()
        var index = 0
        children.forEach { view ->
            val left = (index % 7) * iWidth
            val top = (index / 7) * iHeight
            view.layout(left.toInt(), top.toInt(), (left + iWidth).toInt(), (top + iHeight).toInt())
            index++
        }
    }
}