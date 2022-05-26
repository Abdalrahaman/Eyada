package com.omranic.eyada

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.omranic.eyada.databinding.ActivityMainBinding
import com.omranic.eyada.viewmodel.ConnectivityLiveData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get Navigation Controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        initUIComponents()
    }

    private fun initUIComponents(){
        // app tool bar
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.home_page_fragment, R.id.doctor_page_fragment, R.id.appointment_page_fragment, R.id.account_page_fragment))
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)
        // bottom navigation bar
        binding.bottomNavigationBar.setupWithNavController(navController)
    }
}