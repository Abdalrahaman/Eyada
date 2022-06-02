package com.omranic.eyada.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "available_doctors")
class AvailableDoctor (
    @PrimaryKey
    val id: Int,
    val doctor: Doctor
)