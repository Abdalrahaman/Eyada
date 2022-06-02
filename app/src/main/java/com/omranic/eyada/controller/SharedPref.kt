package com.omranic.eyada.controller

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    private var mySharedPreferences: SharedPreferences
    init {
        mySharedPreferences = context.getSharedPreferences("theme", Context.MODE_PRIVATE)
    }

    fun setNightModeState(state: Boolean){
        val editor = mySharedPreferences.edit()
        editor.putBoolean("isDarkMode", state)
        editor.apply()
    }

    fun getNightModeState(): Boolean{
        return mySharedPreferences.getBoolean("isDarkMode", true)
    }
}