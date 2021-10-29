package com.teamcatchme.calendar_customview

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*

class CustomCalendarAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var startTime: Calendar = Calendar.getInstance()

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        val millsId = getItemId(position)
        return CustomCalendarFragment.newInstance(millsId)
    }

    override fun getItemId(position: Int): Long {
        val start = startTime.clone() as Calendar
        start.add(Calendar.MONTH, position - START_POSITION)
        return start.timeInMillis
    }

    companion object {
        const val START_POSITION = Int.MAX_VALUE / 2
    }


}