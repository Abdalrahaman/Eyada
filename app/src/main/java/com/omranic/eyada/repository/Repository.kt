package com.omranic.eyada.repository

import androidx.lifecycle.LiveData
import com.omranic.eyada.db.EyadaDao
import com.omranic.eyada.model.*
import com.omranic.eyada.network.EyadaApiService
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val eyadaApiService: EyadaApiService,
    private val eyadaDao: EyadaDao
) {

    /* Access Data from Internet */

    suspend fun login(userName: String, password: String): Response<PatientResponse> {
        return eyadaApiService.login(userName, password)
    }

    suspend fun signUp(
        userName: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
        address: String,
        city: String
    ): Response<PatientResponse> {
        return eyadaApiService.signUp(userName, email, password, firstName, lastName, phone, address, city)
    }

    // get ads
    suspend fun getAds(): Response<List<Ad>> {
        return eyadaApiService.getAds()
    }

    // get available doctors
    suspend fun getAvailableDoctors(): Response<List<AvailableDoctor>> {
        return eyadaApiService.getAvailableDoctors()
    }

    // get all doctors
    suspend fun getDoctors(): Response<List<Doctor>> {
        return eyadaApiService.getDoctors()
    }

    // get all doctors by specialist
    suspend fun getDoctorsBySpecialist(specialist: String): Response<List<Doctor>> {
        return eyadaApiService.getDoctorsBySpecialist(specialist)
    }

    // get all appointments to patient
    suspend fun getAppointments(patientId: Int): Response<List<Appointment>> {
        return eyadaApiService.getAppointments(patientId)
    }

    /* Access Data from local Database */
    fun insertAdsToDB(ads: List<Ad>) {
        eyadaDao.insertAds(ads)
    }

    fun insertAvailableDoctorsToDB(doctors: List<AvailableDoctor>) {
        eyadaDao.insertAvailableDoctors(doctors)
    }

    fun getAdsFromDB(): List<Ad> = eyadaDao.getAds()
    fun getAvailableDoctorsFromDB(): List<AvailableDoctor> = eyadaDao.getAvailableDoctors()

    fun insertDoctorsToDB(doctors: List<Doctor>) {
        eyadaDao.insertDoctors(doctors)
    }

    fun getDoctorsFromDB(): LiveData<List<Doctor>> = eyadaDao.getDoctors()


}