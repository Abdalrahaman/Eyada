package com.omranic.eyada.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.omranic.eyada.model.Clinic
import com.omranic.eyada.model.Doctor

class Converters {
    @TypeConverter
    fun fromClinicToGson(clinic: Clinic) : String = Gson().toJson(clinic)
    @TypeConverter
    fun fromGsonToClinic(stringClinic: String) : Clinic = Gson().fromJson(stringClinic, Clinic::class.java)

    @TypeConverter
    fun fromDoctorToGson(doctor: Doctor) : String = Gson().toJson(doctor)
    @TypeConverter
    fun fromGsonToDoctor(stringDoctor: String) : Doctor = Gson().fromJson(stringDoctor, Doctor::class.java)

}