package com.omranic.eyada.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.omranic.eyada.databinding.ActivityDoctorInfoBinding

class DoctorInfoActivity : AppCompatActivity() {

    private var _binding: ActivityDoctorInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDoctorInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}