package com.teamcatchme.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.teamcatchme.catchmesample.databinding.ActivityCalendarBinding
import java.time.LocalDate

class CalendarActivity : AppCompatActivity() {
    companion object {
        const val RIGHT = 1
        const val LEFT = -1
    }

    private fun onArrowClicked(direction: Int) {
        binding.viewpagerCalendar.setCurrentItem(
            binding.viewpagerCalendar.currentItem + direction,
            true
        )
    }

    lateinit var binding: ActivityCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val monthAdapter = MonthAdapter()
        val dateTime = LocalDate.now()
        val currentYear = dateTime.year
        val currentMonth = dateTime.month.value
        Log.d("태그", "set current month ${36 + currentMonth}")
        binding.viewpagerCalendar.adapter = monthAdapter
        binding.viewpagerCalendar.currentItem = 35 + currentMonth
        updateTxtUI(currentYear, currentMonth)
        binding.viewpagerCalendar.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateTxtUI(currentYear - 3 + (position / 12), (position % 12) + 1)
            }
        })
        binding.btnReportDialogMoveLeft.setOnClickListener { onArrowClicked(LEFT) }
        binding.btnReportDialogMoveRight.setOnClickListener { onArrowClicked(RIGHT) }
    }

    fun updateTxtUI(year: Int, month: Int) {
        val txtYearMonth = "$year - $month"
        binding.txtCalendarYearmonth.text = txtYearMonth
    }
}