package com.omranic.eyada.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omranic.eyada.model.Doctor
import com.omranic.eyada.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val TAG = "DoctorViewModel"
    private var doctors = MutableLiveData<List<Doctor>>()
    private var availableDoctors = MutableLiveData<List<Doctor>>()

    fun getDoctors(): LiveData<List<Doctor>> {
        viewModelScope.launch {
            loadDoctors()
        }
        return doctors
    }

    fun getAvailableDoctors(): LiveData<List<Doctor>> {
        viewModelScope.launch {
            loadAvailableDoctors()
        }
        return availableDoctors
    }

    fun getDoctorsBySpecialist(specialist: String): LiveData<List<Doctor>> {
        viewModelScope.launch {
            loadDoctorsBySpecialist(specialist)
        }
        return doctors
    }


    private suspend fun loadDoctors() {
        val response = repository.getDoctors()
        if (response.isSuccessful){
            doctors.value = response.body()
        }
    }

    private suspend fun loadAvailableDoctors() {
        val response = repository.getAvailableDoctors()
        if (response.isSuccessful){
            availableDoctors.value = response.body()
        }
    }

    private suspend fun loadDoctorsBySpecialist(specialist: String) {
        val response = repository.getDoctorsBySpecialist(specialist)
        if (response.isSuccessful){
            doctors.value = response.body()
        }
    }
}