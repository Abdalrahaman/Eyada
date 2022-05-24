package com.omranic.eyada.adapter.appointment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omranic.eyada.R
import com.omranic.eyada.databinding.AppointmentItemBinding
import com.omranic.eyada.model.Appointment

class AppointmentAdapter : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    private lateinit var _appointmentItemBinding: AppointmentItemBinding
    private var appointments = listOf<Appointment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        _appointmentItemBinding =
            AppointmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppointmentViewHolder(_appointmentItemBinding)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return appointments.size
    }

    fun setAppointmentsData(newAppointments: List<Appointment>) {
        appointments = newAppointments
        notifyDataSetChanged()
    }

    inner class AppointmentViewHolder(private val binding: AppointmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int) {
            binding.tvDoctorName.text = appointments[index].doctor.name
            binding.tvSpecialist.text = appointments[index].doctor.specialist
            Glide.with(binding.root).load(appointments[index].doctor.image).into(binding.ivDoctorImage)
            binding.tvDate.text = appointments[index].date
            binding.tvTime.text = appointments[index].time

            if (appointments[index].isConfirmed) {
                binding.tvStatus.text = "Confirmed"
                binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_baseline_confirmed_24,
                    0,
                    0,
                    0
                )
            } else {
                binding.tvStatus.text = "Pending"
                binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_baseline_pending_24,
                    0,
                    0,
                    0
                )
            }
        }
    }
}