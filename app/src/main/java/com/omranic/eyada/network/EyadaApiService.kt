package com.omranic.eyada.network

import com.omranic.eyada.model.Ad
import com.omranic.eyada.model.Appointment
import com.omranic.eyada.model.AvailableDoctor
import com.omranic.eyada.model.Doctor
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EyadaApiService {
    @GET("backend/select_doctor_info.php")
    suspend fun getDoctors(): Response<List<Doctor>>
    @GET("backend/select_available_doctor_info.php")
    suspend fun getAvailableDoctors(): Response<List<AvailableDoctor>>
    @GET("backend/select_ads.php")
    suspend fun getAds(): Response<List<Ad>>
    @GET("backend/select_appointment.php")
    suspend fun getAppointments(@Query("patient_id") patientId: Int): Response<List<Appointment>>
    @GET("backend/select_specialist.php")
    suspend fun getDoctorsBySpecialist(@Query("specialist") specialist: String): Response<List<Doctor>>
}