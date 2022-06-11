package com.omranic.eyada.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.omranic.eyada.R
import com.omranic.eyada.controller.SharedPref
import com.omranic.eyada.databinding.FragmentLoginBinding
import com.omranic.eyada.util.Resource
import com.omranic.eyada.viewmodel.PatientAuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val TAG = "LoginFragment"
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val patientAuthViewModel: PatientAuthViewModel by activityViewModels()

    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(context!!)

        if (sharedPref.getPatientLoginState()) {
            view.findNavController().navigate(R.id.action_login_page_fragment_to_home_page_fragment)
        }

        initUI()

        patientAuthViewModel.patientLoginResult.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        sharedPref.setPatientLoginState(
                            it.id,
                            it.firstName,
                            it.lastName,
                            it.age,
                            it.phone,
                            it.address.address,
                            it.address.city,
                            true
                        )
                        binding.progressIndicator.visibility = View.GONE
                        binding.root.findNavController()
                            .navigate(R.id.action_login_page_fragment_to_home_page_fragment)
                    }
                }
                is Resource.Error -> {
                    binding.progressIndicator.visibility = View.GONE
                    response.message?.let {
                        when (it) {
                            getString(R.string.email_error) -> binding.tilUserName.error =
                                getString(R.string.email_error)
                            getString(R.string.password_error) -> binding.tilPassword.error =
                                getString(R.string.password_error)
                            else -> binding.tvNetworkError.text = it
                        }
                    }
                }
                is Resource.Loading -> {
                    binding.progressIndicator.visibility = View.VISIBLE
                }
            }
        })

        binding.tifUserName.doOnTextChanged { text, _, _, _ ->
            binding.tilUserName.error = null
        }

        binding.tifPassword.doOnTextChanged { text, _, _, _ ->
            binding.tilPassword.error = null
        }

        binding.btLogin.setOnClickListener {
            val userName = binding.tifUserName.text?.trim().toString()
            val password = binding.tifPassword.text?.trim().toString()
            patientAuthViewModel.patientLogin(userName, password)
        }

        binding.tvCreateAccount.setOnClickListener {
            binding.root.findNavController()
                .navigate(R.id.action_login_page_fragment_to_sign_up_fragment)
        }
    }

    private fun initUI() {
        // app tool bar
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.login_page_fragment))
        activity?.findNavController(R.id.nav_host_fragment)
            ?.let { binding.topAppBar.setupWithNavController(it, appBarConfiguration) }
    }
}