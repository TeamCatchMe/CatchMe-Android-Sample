package com.teamcatchme.calendar_customview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teamcatchme.catchmesample.databinding.FragmentCustomCalendarBinding
import java.util.*

class CustomCalendarFragment(private val millsId: Long) : Fragment() {
    private var _binding: FragmentCustomCalendarBinding? = null
    private val binding: FragmentCustomCalendarBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomCalendarBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {
        val date = Date(millsId)
        binding.text.text = date.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}