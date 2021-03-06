package com.omranic.eyada.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.omranic.eyada.R
import com.omranic.eyada.adapter.appointment.AppointmentAdapter
import com.omranic.eyada.databinding.FragmentAppointmentBinding
import com.omranic.eyada.util.Resource
import com.omranic.eyada.viewmodel.AppointmentViewModel
import com.omranic.eyada.viewmodel.ConnectivityLiveData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppointmentFragment : Fragment() {

    private val TAG = "AppointmentFragment"
    private var _binding: FragmentAppointmentBinding?= null
    private val binding get() = _binding!!

    private val appointmentViewModel: AppointmentViewModel by activityViewModels()

    @Inject
    lateinit var connectivityLiveData: ConnectivityLiveData

    private lateinit var appointmentAdapter: AppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){

        }
    }

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

        connectivityLiveData.observe(viewLifecycleOwner, Observer { isConnected ->
            if (isConnected){
                Log.d(TAG, "error not occurred: connected")
                appointmentViewModel.getAppointments(1)
            }else{
                Log.e(TAG, "error occurred: no internet connection")
            }
        })

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
                        Log.e(TAG, "error occurred: $it")
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
        // app tool bar
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.appointment_page_fragment))
        activity?.findNavController(R.id.nav_host_fragment)
            ?.let { binding.topAppBar.setupWithNavController(it, appBarConfiguration) }
        // initialize Appointment Adapter Adapter
        appointmentAdapter = AppointmentAdapter()
        binding.rvAppointment.adapter = appointmentAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}