package com.omranic.eyada.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omranic.eyada.databinding.AdItemBinding
import com.omranic.eyada.model.Ad

class AdAdapter : RecyclerView.Adapter<AdAdapter.AdViewHolder>() {

    private lateinit var _adItemBinding: AdItemBinding
    private var ads = listOf<Ad>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        _adItemBinding = AdItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdViewHolder(_adItemBinding)
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return ads.size
    }

    fun setAdsData(newAds: List<Ad>) {
        ads = newAds
        notifyDataSetChanged()
    }

    inner class AdViewHolder(private val binding: AdItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int) {
            binding.tvHeader.text = ads[index].header
            binding.tvDoctorName.text = ads[index].name
            binding.tvSpecialist.text = ads[index].specialist
            binding.tvClinic.text = ads[index].clinicName
            Glide.with(binding.root).load(ads[index].image).into(binding.ivDoctorImage)
        }
    }
}