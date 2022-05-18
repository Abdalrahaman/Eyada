package com.omranic.eyada.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.omranic.eyada.adapter.home.AdAdapter
import com.omranic.eyada.adapter.home.CategoryAdapter
import com.omranic.eyada.adapter.home.AvailableDoctorAdapter
import com.omranic.eyada.databinding.FragmentHomeBinding
import com.omranic.eyada.viewmodel.AdViewModel
import com.omranic.eyada.viewmodel.CategoryViewModel
import com.omranic.eyada.viewmodel.DoctorViewModel
import kotlin.math.abs

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val adViewModel: AdViewModel by activityViewModels()
    private val categoryViewModel: CategoryViewModel by activityViewModels()
    private val doctorViewModel: DoctorViewModel by activityViewModels()

    private val handler = Handler(Looper.getMainLooper())

    private lateinit var adAdapter: AdAdapter
    private lateinit var doctorAdapter: AvailableDoctorAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize UI Components
        initUI()

        adViewModel.getAds().observe(viewLifecycleOwner, Observer {
            adAdapter.setAdsData(it)
        })

        categoryViewModel.getCategories().observe(viewLifecycleOwner, Observer {
            val categoryAdapter = CategoryAdapter(it)
            binding.rvCategories.adapter = categoryAdapter
        })

        doctorViewModel.getAvailableDoctors().observe(viewLifecycleOwner, Observer {
            doctorAdapter.setAvailableDoctorData(it)
        })

    }

    private fun initUI(){
        // initialize Ads Adapter
        adAdapter = AdAdapter()
        binding.viewpager2.adapter = adAdapter
        binding.viewpager2.offscreenPageLimit = 3
        binding.viewpager2.clipToPadding = false
        binding.viewpager2.clipChildren = false
        binding.viewpager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        setUpTranformer()
        binding.viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 3000)
            }
        })
        // initialize Available Doctor Adapter
        doctorAdapter = AvailableDoctorAdapter()
        binding.rvAvailableDoctor.adapter = doctorAdapter
    }

    private fun setUpTranformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer{ page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        binding.viewpager2.setPageTransformer(transformer)
    }

    private val runnable = Runnable {
        binding.viewpager2.setCurrentItem(binding.viewpager2.currentItem + 1)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}