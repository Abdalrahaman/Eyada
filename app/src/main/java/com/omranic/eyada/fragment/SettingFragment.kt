package com.omranic.eyada.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.omranic.eyada.R
import com.omranic.eyada.controller.SharedPref
import com.omranic.eyada.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private val TAG = "SettingFragment"
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this){

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        sharedPref = SharedPref(context!!)

        binding.lyTheme.setOnClickListener {
            if (sharedPref.getNightModeState()){
                goToLightMode()
                sharedPref.setNightModeState(false)
            }else{
                goToDarkMode()
                sharedPref.setNightModeState(true)
            }
        }
    }

    private fun initUI(){
        // app tool bar
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.setting_page_fragment))
        activity?.findNavController(R.id.nav_host_fragment)
            ?.let { binding.topAppBar.setupWithNavController(it, appBarConfiguration) }
    }

    private fun goToDarkMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun goToLightMode(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}