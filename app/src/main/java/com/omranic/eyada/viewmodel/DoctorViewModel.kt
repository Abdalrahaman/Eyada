package com.omranic.eyada.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class DoctorViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val doctors : MutableLiveData<Resource<List<Doctor>>> = MutableLiveData()
    val availableDoctors : MutableLiveData<Resource<List<Doctor>>> = MutableLiveData()

    fun getDoctors() = viewModelScope.launch {
        doctors.postValue(Resource.Loading())
        try {
            val response = repository.getDoctors()
            doctors.postValue(handleDoctorsResponse(response))
            insertDoctorToDB(response.body()!!)
        }catch (t: Throwable){
            when(t){
                is IOException -> doctors.postValue(Resource.Error("Network Failure"))
                else -> doctors.postValue(Resource.Error(t.message.toString())) // "Conversion Error"
            }
        }
    }

    fun getAvailableDoctors() = viewModelScope.launch {
        availableDoctors.postValue(Resource.Loading())
        try {
            val response = repository.getAvailableDoctors()
            availableDoctors.postValue(handleDoctorsResponse(response))
        }catch (t: Throwable){
            when(t){
                is IOException -> availableDoctors.postValue(Resource.Error("Network Failure"))
                else -> availableDoctors.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    fun getDoctorsBySpecialist(specialist: String) = viewModelScope.launch {
        doctors.postValue(Resource.Loading())
        try {
            val response = repository.getDoctorsBySpecialist(specialist)
            doctors.postValue(handleDoctorsResponse(response))
        }catch (t: Throwable){
            when(t){
                is IOException -> doctors.postValue(Resource.Error("Network Failure"))
                else -> doctors.postValue(Resource.Error("Conversion Error"))
            }
        }

    }

    private fun handleDoctorsResponse(response: Response<List<Doctor>>) : Resource<List<Doctor>>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun insertDoctorToDB(doctors: List<Doctor>){
        repository.insertDoctorToDB(doctors)
    }

//    fun getDoctorsFromDB(){
//        repository.getDoctorsFromDB()
//    }
}