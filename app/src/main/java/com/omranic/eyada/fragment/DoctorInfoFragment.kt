package com.omranic.eyada.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.omranic.eyada.R
import com.omranic.eyada.adapter.appointment.DatePickerAdapter
import com.omranic.eyada.controller.EyadaDatePicker
import com.omranic.eyada.databinding.FragmentDoctorInfoBinding
import com.omranic.eyada.model.Doctor

class DoctorInfoFragment : Fragment() {

    private val TAG = "DoctorInfoFragment"
    private var _binding: FragmentDoctorInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var datePickerAdapter: DatePickerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        val doctor = arguments?.getParcelable<Doctor>("doctor")
        doctor?.let {
            Log.d(TAG, "onCreate: ${it.name}")
        }

        // add date picker
        val pickers = EyadaDatePicker().getWeekFromNow()
        datePickerAdapter.setPickersData(pickers)

    }


    private fun initUI(){
        // app tool bar
        val navController = activity?.findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = navController?.let { AppBarConfiguration(it.graph) }
        binding.collapsingToolbarLayout.setupWithNavController(binding.topAppBar, navController!!, appBarConfiguration!!)

        datePickerAdapter = DatePickerAdapter()
        binding.bookLayout.rvDatePicker.adapter = datePickerAdapter
    }

}