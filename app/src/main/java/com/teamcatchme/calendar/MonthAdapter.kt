package com.teamcatchme.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamcatchme.catchmesample.databinding.ItemCalendarMonthBinding
import java.time.LocalDate

class MonthAdapter : RecyclerView.Adapter<MonthAdapter.ViewHolder>() {
    private val yearList = mutableListOf<CalendarYearMonthData>()

    init {
        val dateTime = LocalDate.now()
        val currentYear = dateTime.year
        for (year in currentYear - 3..currentYear) {
            for (month in 1..12) {
                yearList.add(CalendarYearMonthData(year = year, month = month))
            }
        }
    }

    class ViewHolder(private val dataBinding: ItemCalendarMonthBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun onBind(year: Int, month: Int) {
            val txtYearMonth = year.toString() + "-" + String.format("%02d", month)
            val calendarAdapter = CalendarAdapter(txtYearMonth)
            calendarAdapter.setCachuData(hashMapOf(5 to 1))
            dataBinding.rcvCalendar.adapter = calendarAdapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCalendarMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("태그", "$position, ${yearList[position]}")
        holder.onBind(yearList[position].year, yearList[position].month)
    }

    override fun getItemCount(): Int = yearList.size

}