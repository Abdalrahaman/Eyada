package com.omranic.eyada.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.omranic.eyada.adapter.doctor.DoctorAdapter
import com.omranic.eyada.databinding.FragmentDoctorBinding
import com.omranic.eyada.viewmodel.DoctorViewModel

class DoctorFragment : Fragment() {

    private var _binding: FragmentDoctorBinding?= null
    private val binding get() = _binding!!

    private val doctorViewModel: DoctorViewModel by activityViewModels()

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

        doctorViewModel.getDoctors().observe(viewLifecycleOwner, Observer {
            doctorAdapter.setDoctorData(it)
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