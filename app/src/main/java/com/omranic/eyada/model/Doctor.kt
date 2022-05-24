package com.omranic.eyada.model

import java.io.Serializable

class Doctor(val name: String, val specialist: String, val experience: Int = 0, val patientNumber: String = "", val image: String, val rate: Float = 0.0f, val clinic: Clinic) : Serializable