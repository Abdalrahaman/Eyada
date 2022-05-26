package com.omranic.eyada.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.omranic.eyada.adapter.appointment.AppointmentAdapter
import com.omranic.eyada.databinding.FragmentAppointmentBinding
import com.omranic.eyada.util.Resource
import com.omranic.eyada.viewmodel.AppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentFragment : Fragment() {

    private val TAG = "AppointmentFragment"
    private var _binding: FragmentAppointmentBinding?= null
    private val binding get() = _binding!!

    private val appointmentViewModel: AppointmentViewModel by activityViewModels()

    private lateinit var appointmentAdapter: AppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI Components
        initUI()

        appointmentViewModel.appointments.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success -> {
                    binding.progressIndicator.visibility = View.INVISIBLE
                    binding.rvAppointment.visibility = View.VISIBLE
                    response.data.let {
                        appointmentAdapter.setAppointmentsData(it!!)
                    }
                }
                is Resource.Error -> {
                    binding.progressIndicator.visibility = View.INVISIBLE
                    binding.rvAppointment.visibility = View.INVISIBLE
                    response.message.let {
                        Log.e(TAG, "error occured: $it")
                    }
                }
                is Resource.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                    binding.rvAppointment.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun initUI(){
        // initialize Appointment Adapter Adapter
        appointmentAdapter = AppointmentAdapter()
        binding.rvAppointment.adapter = appointmentAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}