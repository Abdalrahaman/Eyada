package com.omranic.eyada.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omranic.eyada.model.Appointment
import com.omranic.eyada.model.Doctor
import com.omranic.eyada.repository.Repository
import com.omranic.eyada.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val TAG = "AppointmentViewModel"
    val appointments : MutableLiveData<Resource<List<Appointment>>> = MutableLiveData()

    init {
//        getAppointments(1)
    }

    fun getAppointments(patientId: Int) = viewModelScope.launch {
        appointments.postValue(Resource.Loading())
        try {
            val response = repository.getAppointments(patientId)
            appointments.postValue(handleAppointmentsResponse(response))
        }catch (t: Throwable){
            when(t){
                is IOException -> appointments.postValue(Resource.Error("Network Failure"))
                else -> appointments.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleAppointmentsResponse(response: Response<List<Appointment>>) : Resource<List<Appointment>>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}