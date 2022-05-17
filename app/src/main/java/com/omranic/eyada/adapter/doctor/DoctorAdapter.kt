package com.omranic.eyada.adapter.doctor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omranic.eyada.databinding.DoctorGridItemBinding
import com.omranic.eyada.model.Doctor
import com.omranic.eyada.ui.DoctorInfoActivity

class DoctorAdapter : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    private lateinit var _doctorGridItemBinding: DoctorGridItemBinding
    private var doctors = listOf<Doctor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        _doctorGridItemBinding =
            DoctorGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorViewHolder(_doctorGridItemBinding)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return doctors.size
    }

    fun setDoctorData(newDoctors: List<Doctor>) {
        doctors = newDoctors
        notifyDataSetChanged()
    }

    inner class DoctorViewHolder(private val binding: DoctorGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(index: Int) {
            binding.tvDoctorName.text = doctors[index].name
            binding.tvSpecialist.text = doctors[index].specialist
            binding.tvPatientNumber.text = doctors[index].patientNumber
            binding.tvExperienceNumber.text = doctors[index].experience.toString()
            binding.ratingbar.rating = doctors[index].rate
            Glide.with(binding.root).load(doctors[index].image).into(binding.ivDoctorImage)

            binding.cardView.setOnClickListener(View.OnClickListener {
                val intent = Intent(it.context, DoctorInfoActivity::class.java)
                it.context.startActivity(intent)
            })
        }
    }
}