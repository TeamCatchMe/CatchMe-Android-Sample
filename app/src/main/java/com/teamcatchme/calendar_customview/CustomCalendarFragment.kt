package com.teamcatchme.calendar_customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teamcatchme.catchmesample.databinding.FragmentCustomCalendarBinding
import java.util.*
import kotlin.properties.Delegates

class CustomCalendarFragment : Fragment() {

    companion object {
        private var MILLS_ID: String = "MILLS_ID"
        private var millsId: Long = 0L

        fun newInstance(millsId: Long): CustomCalendarFragment {
            val fragment = CustomCalendarFragment()
            val args = Bundle()
            args.putLong(MILLS_ID, millsId)
            fragment.arguments = args

            return fragment
        }
    }

    private var _binding: FragmentCustomCalendarBinding? = null
    private val binding: FragmentCustomCalendarBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomCalendarBinding.inflate(inflater, container, false)
        arguments?.let {
            millsId = it.getLong(MILLS_ID)
        }
        initView()
        return binding.root
    }

    private fun initView() {
        val date = Date(millsId)
        binding.text.text = date.toString()
        binding.calendarView.drawCalendar(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}