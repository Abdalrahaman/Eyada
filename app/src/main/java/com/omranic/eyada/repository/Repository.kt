package com.omranic.eyada.repository

import androidx.lifecycle.LiveData
import com.omranic.eyada.db.EyadaDao
import com.omranic.eyada.model.Ad
import com.omranic.eyada.model.Appointment
import com.omranic.eyada.model.Doctor
import com.omranic.eyada.network.EyadaApiService
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val eyadaApiService: EyadaApiService, private val eyadaDao: EyadaDao) {

    /* Access Data from Internet */

    // get ads
    suspend fun getAds(): Response<List<Ad>> {
        return eyadaApiService.getAds()
    }

    // get available doctors
    suspend fun getAvailableDoctors(): Response<List<Doctor>> {
        return eyadaApiService.getAvailableDoctors()
    }

    // get all doctors
    suspend fun getDoctors(): Response<List<Doctor>> {
        return eyadaApiService.getDoctors()
    }

    // get all appointments to patient
    suspend fun getAppointments(patientId: Int): Response<List<Appointment>> {
        return eyadaApiService.getAppointments(patientId)
    }

    // get all doctors by specialist
    suspend fun getDoctorsBySpecialist(specialist: String): Response<List<Doctor>> {
        return eyadaApiService.getDoctorsBySpecialist(specialist)
    }

    /* Access Data from local Database */
    fun insertDoctorToDB(doctors: List<Doctor>){
        eyadaDao.insertDoctors(doctors)
    }

    fun getDoctorsFromDB() : LiveData<List<Doctor>> = eyadaDao.getDoctors()
}