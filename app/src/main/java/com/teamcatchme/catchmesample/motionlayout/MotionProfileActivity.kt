package com.teamcatchme.catchmesample.motionlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.AppBarLayout
import com.teamcatchme.catchmesample.R
import com.teamcatchme.catchmesample.databinding.ActivityMotionProfileBinding

class MotionProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMotionProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_motion_profile)
        coordinateMotion()
    }

    private fun coordinateMotion() {
        binding.appbarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val seekPosition = -verticalOffset / binding.appbarLayout.totalScrollRange.toFloat()
            binding.motionLayout.progress = seekPosition
        })
    }
}