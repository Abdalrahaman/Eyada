package com.omranic.eyada.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omranic.eyada.model.AvailableDoctor
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
    val availableDoctors : MutableLiveData<Resource<List<AvailableDoctor>>> = MutableLiveData()

    fun getDoctors() = viewModelScope.launch {
        doctors.postValue(Resource.Loading())
        try {
            val response = repository.getDoctors()
            doctors.postValue(handleDoctorsResponse(response))
            insertDoctorsToDB(response.body()!!)
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
            availableDoctors.postValue(handleAvailableDoctorsResponse(response))
            insertAvailableDoctorsToDB(response.body()!!)
        }catch (t: Throwable){
            when(t){
                is IOException -> availableDoctors.postValue(Resource.Error("Network Failure", getAvailableDoctorsFromDB()))
                else -> availableDoctors.postValue(Resource.Error(t.message.toString())) //"Conversion Error"
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

    private fun handleAvailableDoctorsResponse(response: Response<List<AvailableDoctor>>) : Resource<List<AvailableDoctor>>{
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun insertDoctorsToDB(doctors: List<Doctor>){
        repository.insertDoctorsToDB(doctors)
    }

    private fun insertAvailableDoctorsToDB(doctors: List<AvailableDoctor>){
        repository.insertAvailableDoctorsToDB(doctors)
    }
    private fun getAvailableDoctorsFromDB(): List<AvailableDoctor> = repository.getAvailableDoctorsFromDB()

//    fun getDoctorsFromDB(){
//        repository.getDoctorsFromDB()
//    }
}