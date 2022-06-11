package com.omranic.eyada.controller

import android.content.Context
import android.content.SharedPreferences
import com.omranic.eyada.model.Address
import com.omranic.eyada.model.Patient

class SharedPref(context: Context) {
    private var mySharedPreferences: SharedPreferences
    private var patientSharedPreferences: SharedPreferences

    init {
        mySharedPreferences = context.getSharedPreferences("theme", Context.MODE_PRIVATE)
        patientSharedPreferences =
            context.getSharedPreferences("patientLogin", Context.MODE_PRIVATE)
    }

    fun setNightModeState(state: Boolean) {
        val editor = mySharedPreferences.edit()
        editor.putBoolean("isDarkMode", state)
        editor.apply()
    }

    fun getNightModeState(): Boolean {
        return mySharedPreferences.getBoolean("isDarkMode", true)
    }

    fun setPatientLoginState(
        id: Int,
        firstName: String,
        lastName: String,
        age: Int,
        phone: String,
        address: String,
        city: String,
        state: Boolean
    ) {
        val editor = patientSharedPreferences.edit()
        editor.putInt("patient_id", id)
        editor.putString("first_name", firstName)
        editor.putString("last_name", lastName)
        editor.putInt("age", age)
        editor.putString("phone", phone)
        editor.putString("address", address)
        editor.putString("city", city)
        editor.putBoolean("isLogin", state)
        editor.apply()
    }

    fun getPatientLoginState(): Boolean {
        return patientSharedPreferences.getBoolean("isLogin", false)
    }

    fun getPatientLoginInfo(): Patient {
        return Patient(
            patientSharedPreferences.getInt("patient_id", 0),
            patientSharedPreferences.getString("first_name", "")!!,
            patientSharedPreferences.getString("last_name", "")!!,
            patientSharedPreferences.getInt("age", 0),
            patientSharedPreferences.getString("phone", "")!!,
            Address(
                patientSharedPreferences.getString("address", "")!!,
                patientSharedPreferences.getString("city", "")!!
            ),
        )
    }
}