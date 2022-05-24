package com.omranic.eyada.adapter.appointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omranic.eyada.R
import com.omranic.eyada.databinding.DatePickerItemBinding
import com.omranic.eyada.model.Pick

class DatePickerAdapter : RecyclerView.Adapter<DatePickerAdapter.DatePickerViewHolder>() {

    private lateinit var _datePickerLayoutBinding: DatePickerItemBinding
    private var pickers = listOf<Pick>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatePickerViewHolder {
        _datePickerLayoutBinding = DatePickerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DatePickerViewHolder(_datePickerLayoutBinding)
    }

    override fun onBindViewHolder(holder: DatePickerViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return pickers.size
    }

    fun setPickersData(newPickers: List<Pick>) {
        pickers = newPickers
        notifyDataSetChanged()
    }

    inner class DatePickerViewHolder(private val binding: DatePickerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int){
            binding.tvDayName.text = pickers[index].dayName.substring(startIndex = 0, endIndex = 3)
            binding.tvDayNumber.text = pickers[index].dayNumber.toString()
            binding.pickCardView.setOnClickListener {
                binding.pickCardView.setCardBackgroundColor(R.color.md_theme_dark_tertiary)
            }
        }
    }
}