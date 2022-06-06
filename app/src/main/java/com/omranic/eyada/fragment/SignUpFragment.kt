package com.omranic.eyada.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.omranic.eyada.R
import com.omranic.eyada.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        binding.btNext.setOnClickListener {
            val bundle = bundleOf(
                "user_name" to binding.tifUserName.text.toString(),
                "email" to binding.tifEmail.text.toString(),
                "password" to binding.tifPassword.text.toString()
            )
            binding.root.findNavController()
                .navigate(R.id.action_sign_up_fragment_to_sign_up_info_fragment, bundle)
        }
    }

    private fun initUI(){
        // app tool bar
        val navController = activity?.findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = navController?.let { AppBarConfiguration(it.graph) }
        binding.topAppBar.setupWithNavController(navController!!, appBarConfiguration!!)
    }
}