package com.teamcatchme.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.teamcatchme.catchmesample.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val monthAdapter = MonthAdapter()
        binding.viewpagerCalendar.adapter = monthAdapter
        binding.viewpagerCalendar.currentItem = Int.MAX_VALUE

        var lastPosition = Int.MAX_VALUE
        binding.viewpagerCalendar.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(
                position: Int
            ) {
                super.onPageSelected(position)
                when (position > lastPosition) {
                    true -> {
                        monthAdapter.switchNextMonth()
                    }
                    false -> {
                        monthAdapter.switchPreviousMonth()
                    }
                }
                lastPosition = position
            }
        })
    }
}