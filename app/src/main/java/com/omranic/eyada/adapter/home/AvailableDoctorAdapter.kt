package com.omranic.eyada.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omranic.eyada.databinding.AvailableDoctorItemBinding
import com.omranic.eyada.model.Doctor

class AvailableDoctorAdapter : RecyclerView.Adapter<AvailableDoctorAdapter.DoctorViewHolder>() {

    private lateinit var _availableDoctorItemBinding: AvailableDoctorItemBinding
    private var doctors = listOf<Doctor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        _availableDoctorItemBinding =
            AvailableDoctorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorViewHolder(_availableDoctorItemBinding)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return doctors.size
    }

    fun setAvailableDoctorData(newAvailableDoctor: List<Doctor>) {
        doctors = newAvailableDoctor
        notifyDataSetChanged()
    }

    inner class DoctorViewHolder(private val binding: AvailableDoctorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int) {
            binding.tvDoctorName.text = doctors[index].name
            binding.tvSpecialist.text = doctors[index].specialist
            binding.tvExperienceNumber.text = doctors[index].experience.toString()
            binding.tvPatientNumber.text = doctors[index].patientNumber
            binding.ratingbar.rating = doctors[index].rate
            Glide.with(binding.root).load(doctors[index].image).into(binding.ivDoctorImage)
        }
    }
}