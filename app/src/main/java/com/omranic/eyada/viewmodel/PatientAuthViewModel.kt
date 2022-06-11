package com.omranic.eyada.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omranic.eyada.model.Patient
import com.omranic.eyada.model.PatientResponse
import com.omranic.eyada.repository.Repository
import com.omranic.eyada.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PatientAuthViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val patientLoginResult: MutableLiveData<Resource<Patient>> = MutableLiveData()
    val patientSignUpResult: MutableLiveData<Resource<Patient>> = MutableLiveData()

    fun patientLogin(userName: String, password: String) = viewModelScope.launch {
        patientLoginResult.postValue(Resource.Loading())
        try {
            val response = repository.login(userName, password)
            patientLoginResult.postValue(handlePatientLoginResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> patientLoginResult.postValue(Resource.Error("Check your network connection"))//"Check your network connection"
                else -> patientLoginResult.postValue(Resource.Error(t.message.toString())) // "Conversion Error"
            }
        }
    }

    fun patientSignUp(
        userName: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        age: Int,
        phone: String,
        address: String,
        city: String
    ) = viewModelScope.launch {
        patientSignUpResult.postValue(Resource.Loading())
        try {
            val response = repository.signUp(userName, email, password, firstName, lastName, age, phone, address, city)
            patientSignUpResult.postValue(handlePatientSignUpResponse(response))
        } catch (t: Throwable) {
            when (t) {
                is IOException -> patientSignUpResult.postValue(Resource.Error("Check your network connection"))//"Check your network connection"
                else -> patientSignUpResult.postValue(Resource.Error(t.message.toString())) // "Conversion Error"
            }
        }
    }

    private fun handlePatientLoginResponse(response: Response<PatientResponse>): Resource<Patient> {
        if (response.isSuccessful) {
            response.body()?.let {
                when (it.success) {
                    2 -> return Resource.Success(it.patient!!)
                    1 -> return Resource.Error("Password is Incorrect")
                    0 -> return Resource.Error("Email is wrong")
                    else -> {}
                }
            }
        }
        return Resource.Error(response.message())
    }

    private fun handlePatientSignUpResponse(response: Response<PatientResponse>): Resource<Patient> {
        if (response.isSuccessful) {
            response.body()?.let {
                when (it.success) {
                    2 -> return Resource.Success(it.patient!!)
                    0 -> return Resource.Error("Wrong Occur")
                    else -> {}
                }
            }
        }
        return Resource.Error(response.message())
    }

}