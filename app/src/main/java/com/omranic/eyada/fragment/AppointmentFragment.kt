package com.omranic.eyada.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.omranic.eyada.adapter.appointment.AppointmentAdapter
import com.omranic.eyada.databinding.FragmentAppointmentBinding
import com.omranic.eyada.viewmodel.AppointmentViewModel

class AppointmentFragment : Fragment() {

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

        appointmentViewModel.getAppointments(1).observe(viewLifecycleOwner, Observer {
            appointmentAdapter.setAppointmentsData(it)
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