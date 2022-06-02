package com.omranic.eyada.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import javax.annotation.Nullable

@Parcelize
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
) : Parcelable