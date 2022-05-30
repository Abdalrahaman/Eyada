package com.omranic.eyada.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.omranic.eyada.adapter.doctor.DoctorAdapter
import com.omranic.eyada.databinding.FragmentDoctorBinding
import com.omranic.eyada.util.Resource
import com.omranic.eyada.viewmodel.ConnectivityLiveData
import com.omranic.eyada.viewmodel.DoctorViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorFragment : Fragment() {

    private val TAG = "DoctorFragment"
    private var _binding: FragmentDoctorBinding?= null
    private val binding get() = _binding!!

    private val doctorViewModel: DoctorViewModel by activityViewModels()

    @Inject
    lateinit var connectivityLiveData: ConnectivityLiveData

    private lateinit var doctorAdapter: DoctorAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI Components
        initUI()

        connectivityLiveData.observe(viewLifecycleOwner, Observer { isConnected ->
            if (isConnected){
                Log.d(TAG, "error not occurred: connected")
                doctorViewModel.getDoctors()
            }else{
                Log.e(TAG, "error occurred: no internet connection")
            }
        })

        doctorViewModel.doctors.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success -> {
                    binding.progressIndicator.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                    response.data?.let {
                        doctorAdapter.setDoctorData(it)
                    }
                }
                is Resource.Error -> {
                    binding.progressIndicator.visibility = View.INVISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                    response.message?.let {
                        Log.e(TAG, "error occurred: $it")
                    }
                }
                is Resource.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.INVISIBLE
                }
            }
        })

        binding.chipLayout.chipSpecialistGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            when(group.checkedChipId){
                binding.chipLayout.chipAll.id -> doctorViewModel.getDoctors()
                binding.chipLayout.chipCardiologist.id -> doctorViewModel.getDoctorsBySpecialist("Cardiologist")
                binding.chipLayout.chipNeurosurgery.id -> doctorViewModel.getDoctorsBySpecialist("Neurosurgery")
                binding.chipLayout.chipPediatrician.id -> doctorViewModel.getDoctorsBySpecialist("Pediatrician")
                binding.chipLayout.chipPsychically.id -> doctorViewModel.getDoctorsBySpecialist("Psychically")
            }
        }
    }

    private fun initUI(){
        // initialize Doctor Adapter Adapter
        doctorAdapter = DoctorAdapter()
        binding.recyclerView.adapter = doctorAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}