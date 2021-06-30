package com.teamcatchme.catchmesample.motionlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.teamcatchme.catchmesample.R
import com.teamcatchme.catchmesample.databinding.ActivityMotionStarBinding

class MotionStarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMotionStarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_motion_star)
    }
}