package com.teamcatchme.calendar

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamcatchme.catchmesample.R
import com.teamcatchme.catchmesample.databinding.ItemCalendarCachuFalseBinding
import com.teamcatchme.catchmesample.databinding.ItemCalendarCachuTrueBinding
import com.teamcatchme.catchmesample.databinding.ItemCalendarNoBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class CalendarAdapter(private val yearMonth: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dateData = mutableListOf<HashMap<Int, Int>>()
    private val cachuData = HashMap<Int, Int>()

    fun setCachuData(newData: HashMap<Int, Int>) {
        cachuData.clear()
        cachuData.putAll(newData)
        setDateData()
    }

    private fun setDateData() {
        val firstDateFormat = yearMonth + "01"
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("yyyyMMdd").parse(firstDateFormat)
        val firstDateOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1
        val lastDate = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 1..firstDateOfWeek) {
            dateData.add(hashMapOf(-1 to -1))
        }
        for (i in 1..lastDate) {
            val idx = cachuData[i] ?: 0
            dateData.add(hashMapOf(i to idx))
        }
        for (i in firstDateOfWeek + lastDate + 1..42) {
            dateData.add(hashMapOf(-1 to -1))
        }

        notifyDataSetChanged()
        Log.d("태그", cachuData.toString())
        Log.d("태그", dateData.toString())
    }

    override fun getItemViewType(position: Int): Int {
        return dateData[position].values.first()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            -1 -> {
                val binding = ItemCalendarNoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                NoneTypeViewHolder(binding)
            }
            0 -> {
                val binding = ItemCalendarCachuFalseBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DateTypeViewHolder(binding)
            }
            else -> {
                val binding = ItemCalendarCachuTrueBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CachuTypeViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = dateData[position]
        when (obj.values.first()) {
            -1 -> (holder as NoneTypeViewHolder)
            0 -> (holder as DateTypeViewHolder).onBind(obj.keys.first())
            else -> (holder as CachuTypeViewHolder).onBind(obj.keys.first(), obj.values.first())
        }
    }

    override fun getItemCount(): Int = dateData.size

    class NoneTypeViewHolder(binding: ItemCalendarNoBinding) : RecyclerView.ViewHolder(binding.root)

    class DateTypeViewHolder(private val binding: ItemCalendarCachuFalseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(date: Int) {
            binding.txtCalendarDate.text = date.toString()
        }
    }

    class CachuTypeViewHolder(private val binding: ItemCalendarCachuTrueBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(date: Int, cachuIdx: Int) {
            binding.txtCalendarDate.text = date.toString()
            binding.imgCalendarCachu.setImageResource(R.drawable.ic_cachu1)
        }
    }

}