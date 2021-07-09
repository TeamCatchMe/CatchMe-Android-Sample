package com.teamcatchme.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamcatchme.catchmesample.databinding.ItemCalendarMonthBinding
import java.time.LocalDate

class MonthAdapter :
    ListAdapter<String, MonthAdapter.ViewHolder>(CalendarAdapterUtilCallBack()) {
    private val yearList = mutableListOf<String>()

    init {
        val dateTime = LocalDate.now()
        val currentYear = dateTime.year
        for (year in currentYear - 3..currentYear) {
            for (month in 1..12) {
                val txtYearMonth = year.toString() + "-" + String.format("%02d", month)
                yearList.add(txtYearMonth)
            }
        }
        this.submitList(yearList)
    }

    class ViewHolder(private val dataBinding: ItemCalendarMonthBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun onBind(yearMonth: String) {
            val calendarAdapter = CalendarAdapter(yearMonth)
            // 여기서 서버 통신으로 캐츄 데이터를 받아온다
            val cachuData = hashMapOf(5 to 1)
            calendarAdapter.setCachuData(cachuData)
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
        holder.onBind(getItem(position))
    }

    class CalendarAdapterUtilCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }
}