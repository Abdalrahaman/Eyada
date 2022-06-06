package com.omranic.eyada.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.omranic.eyada.R
import com.omranic.eyada.databinding.FragmentSignUpInfoBinding
import com.omranic.eyada.util.Resource
import com.omranic.eyada.viewmodel.PatientAuthViewModel

class SignUpInfoFragment : Fragment() {

    private val TAG = "SignUpInfoFragment"
    private var _binding: FragmentSignUpInfoBinding? = null
    private val binding get() = _binding!!

    private val patientAuthViewModel: PatientAuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        patientAuthViewModel.patientSignUpResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        binding.progressIndicator.visibility = View.GONE
                        binding.root.findNavController()
                            .navigate(R.id.action_sign_up_info_fragment_to_home_page_fragment)
                    }
                }
                is Resource.Error -> {
                    binding.progressIndicator.visibility = View.GONE
                    response.message?.let {
                        when (it) {
                            "Wrong Occur" -> binding.tvNetworkError.text = it
                            else -> {}
                        }
                    }
                }
                is Resource.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }
            }
        })

        binding.btSignUp.setOnClickListener {
            patientAuthViewModel.patientSignUp(
                arguments?.getString("user_name").toString(),
                arguments?.getString("email").toString(),
                arguments?.getString("password").toString(),
                binding.tifFirstName.text.toString(),
                binding.tifLastName.text.toString(),
                binding.tifPhone.text.toString(),
                binding.tifAddress.text.toString(),
                binding.tifCity.text.toString()
            )
        }
    }

    private fun initUI(){
        // app tool bar
        val navController = activity?.findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = navController?.let { AppBarConfiguration(it.graph) }
        binding.topAppBar.setupWithNavController(navController!!, appBarConfiguration!!)
    }
}