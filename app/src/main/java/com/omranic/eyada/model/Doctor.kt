package com.omranic.eyada.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "doctors")
class Doctor(
    @PrimaryKey
    val id: Int,
    val name: String,
    val specialist: String,
    val experience: Int,
    @ColumnInfo(name = "patient_number") val patientNumber: String,
    val image: String,
    val rate: Float,
    val clinic: Clinic
) : Serializable