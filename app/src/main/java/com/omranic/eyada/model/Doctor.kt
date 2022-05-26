package com.omranic.eyada.model

import java.io.Serializable

class Doctor(val name: String, val specialist: String, val experience: Int, val patientNumber: String, val image: String, val rate: Float, val clinic: Clinic) : Serializable