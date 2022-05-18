package com.omranic.eyada.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.omranic.eyada.databinding.ActivityDoctorInfoBinding
import com.omranic.eyada.model.Doctor

class DoctorInfoActivity : AppCompatActivity() {
    private val TAG = "DoctorInfoActivity"

    private var _binding: ActivityDoctorInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDoctorInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val doctor = intent.getSerializableExtra("doctor") as Doctor
        doctor.let {
            Log.d(TAG, "onCreate: ${it.name}")
        }
    }
}