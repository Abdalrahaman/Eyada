package com.omranic.eyada.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.omranic.eyada.R
import com.omranic.eyada.controller.SharedPref
import com.omranic.eyada.databinding.ActivityMainBinding
import com.omranic.eyada.viewmodel.ConnectivityLiveData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPref: SharedPref

    @Inject
    lateinit var connectivityLiveData: ConnectivityLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = SharedPref(this)

        if (sharedPref.getNightModeState()) {
            goToDarkMode()
        } else {
            goToLightMode()
        }

        initUIComponents()

        connectivityLiveData.observe(this, Observer { isConnected ->
            if (isConnected) {
                binding.tvError.visibility = View.GONE
            } else {
                binding.tvError.visibility = View.VISIBLE
            }
        })
    }

    private fun initUIComponents() {
        // get Navigation Controller
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // bottom navigation bar
        binding.bottomNavigationBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id){
                R.id.home_page_fragment,
                R.id.doctor_page_fragment,
                R.id.appointment_page_fragment,
                R.id.setting_page_fragment -> binding.bottomNavigationBar.visibility = View.VISIBLE
                R.id.doctor_info_fragment -> binding.bottomNavigationBar.visibility = View.GONE
            }
        }
    }

    private fun goToDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun goToLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}