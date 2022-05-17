package com.omranic.eyada.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omranic.eyada.model.Appointment
import com.omranic.eyada.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val TAG = "AppointmentViewModel"
    private var appointments = MutableLiveData<List<Appointment>>()

    fun getAppointments(patientId: Int): LiveData<List<Appointment>> {
        viewModelScope.launch {
            loadAppointments(patientId)
        }
        return appointments
    }

    private suspend fun loadAppointments(patientId: Int) {
        val response = repository.getAppointments(patientId)
        if (response.isSuccessful){
            appointments.value = response.body()
        }
    }
}