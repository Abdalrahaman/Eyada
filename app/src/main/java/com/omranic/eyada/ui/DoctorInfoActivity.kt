package com.omranic.eyada.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.omranic.eyada.adapter.appointment.DatePickerAdapter
import com.omranic.eyada.controller.EyadaDatePicker
import com.omranic.eyada.databinding.ActivityDoctorInfoBinding
import com.omranic.eyada.model.Doctor

class DoctorInfoActivity : AppCompatActivity() {
    private val TAG = "DoctorInfoActivity"

    private var _binding: ActivityDoctorInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var datePickerAdapter: DatePickerAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDoctorInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        val doctor = intent.getSerializableExtra("doctor") as Doctor
        doctor.let {
            Log.d(TAG, "onCreate: ${it.name}")
        }

        // add date picker
        val pickers = EyadaDatePicker().getWeekFromNow()
        datePickerAdapter.setPickersData(pickers)
    }

    fun initUI(){
        datePickerAdapter = DatePickerAdapter()
        binding.bookLayout.rvDatePicker.adapter = datePickerAdapter
    }
}