package com.omranic.eyada.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ads")
class Ad(
    @PrimaryKey
    val id: Int,
    val header: String,
    val doctor: Doctor
)