package com.teamcatchme.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamcatchme.catchmesample.databinding.ItemCalendarMonthBinding

class MonthAdapter() : RecyclerView.Adapter<MonthAdapter.ViewHolder>() {
    private var year = 2021
    private var month = 7

    fun switchPreviousMonth() {
        month -= 1
        if (month == 0) {
            month = 12
            year -= 1
        }
        notifyDataSetChanged()
    }

    fun switchNextMonth() {
        month += 1
        if (month == 13) {
            month = 1
            year += 1
        }
        notifyDataSetChanged()
    }

    class ViewHolder(private val dataBinding: ItemCalendarMonthBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun onBind(year: Int, month: Int) {
            val txtYearMonth = year.toString() + "-" + String.format("%02d", month)
            Log.d("태그", txtYearMonth)
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
        holder.onBind(year, month)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

}