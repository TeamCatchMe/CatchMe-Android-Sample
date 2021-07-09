package com.teamcatchme.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.teamcatchme.catchmesample.databinding.ActivityCalendarBinding
import java.time.LocalDate

class CalendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val monthAdapter = MonthAdapter()
        val dateTime = LocalDate.now()
        val currentMonth = dateTime.month.value
        Log.d("태그", "set current month ${36 + currentMonth}")
        binding.viewpagerCalendar.adapter = monthAdapter
        binding.viewpagerCalendar.currentItem = 35 + currentMonth

    }
}