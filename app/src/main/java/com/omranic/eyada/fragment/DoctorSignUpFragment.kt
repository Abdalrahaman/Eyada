package com.omranic.eyada.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.omranic.eyada.databinding.FragmentDoctorSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorSignUpFragment : Fragment() {

    private var _binding: FragmentDoctorSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI(){
        val items = arrayOf("Cardiologist", "Neurosurgery", "Pediatrician", "Psychically")
        (binding.tilSpecialist.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(items)
    }

}