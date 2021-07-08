package com.teamcatchme.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.teamcatchme.catchmesample.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val year = 2021
        val month = 7
        val txtYearMonth = year.toString() + String.format("%02d", month)
        Log.d("태그", txtYearMonth)
        val calendarAdapter = CalendarAdapter(txtYearMonth)
        calendarAdapter.setCachuData(hashMapOf(5 to 1))
        binding.rcvCalendar.adapter = calendarAdapter
        binding.txtCalendarYearmonth.text = txtYearMonth
    }
}