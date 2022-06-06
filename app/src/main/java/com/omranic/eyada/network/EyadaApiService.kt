package com.omranic.eyada.network

import com.omranic.eyada.model.*
import retrofit2.Response
import retrofit2.http.*

interface EyadaApiService {
    @FormUrlEncoded
    @POST("backend/patient_login.php")
    suspend fun login(
        @Field("user_name") userName: String,
        @Field("password") password: String
    ): Response<PatientResponse>

    @FormUrlEncoded
    @POST("backend/patient_register.php")
    suspend fun signUp(
        @Field("user_name") userName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("phone") phone: String,
        @Field("address") address: String,
        @Field("city") city: String
    ): Response<PatientResponse>

    @GET("backend/select_ads.php")
    suspend fun getAds(): Response<List<Ad>>

    @GET("backend/select_available_doctor_info.php")
    suspend fun getAvailableDoctors(): Response<List<AvailableDoctor>>

    @GET("backend/select_doctor_info.php")
    suspend fun getDoctors(): Response<List<Doctor>>

    @GET("backend/select_specialist.php")
    suspend fun getDoctorsBySpecialist(@Query("specialist") specialist: String): Response<List<Doctor>>

    @GET("backend/select_appointment.php")
    suspend fun getAppointments(@Query("patient_id") patientId: Int): Response<List<Appointment>>
}