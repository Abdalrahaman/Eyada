package com.omranic.eyada.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omranic.eyada.databinding.AvailableDoctorItemBinding
import com.omranic.eyada.model.AvailableDoctor
import com.omranic.eyada.model.Doctor

class AvailableDoctorAdapter : RecyclerView.Adapter<AvailableDoctorAdapter.DoctorViewHolder>() {

    private lateinit var _availableDoctorItemBinding: AvailableDoctorItemBinding
    private var doctors = listOf<AvailableDoctor>()

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

    fun setAvailableDoctorData(newAvailableDoctor: List<AvailableDoctor>) {
        doctors = newAvailableDoctor
        notifyDataSetChanged()
    }

    inner class DoctorViewHolder(private val binding: AvailableDoctorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int) {
            binding.tvDoctorName.text = doctors[index].doctor.name
            binding.tvSpecialist.text = doctors[index].doctor.specialist
            binding.tvExperienceNumber.text = doctors[index].doctor.experience.toString() + " Years"
            binding.tvPatientNumber.text = doctors[index].doctor.patientNumber
            binding.ratingbar.rating = doctors[index].doctor.rate
            Glide.with(binding.root).load(doctors[index].doctor.image).into(binding.ivDoctorImage)
        }
    }
}