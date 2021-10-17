package com.teamcatchme.calendar_customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.teamcatchme.catchmesample.R
import com.teamcatchme.catchmesample.databinding.ActivityCustomCalendarBinding

class CustomCalendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityCustomCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}