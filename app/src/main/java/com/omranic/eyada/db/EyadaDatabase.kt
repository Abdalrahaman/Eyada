package com.omranic.eyada.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.omranic.eyada.model.Ad
import com.omranic.eyada.model.AvailableDoctor
import com.omranic.eyada.model.Doctor

@Database(entities = [Doctor::class, Ad::class, AvailableDoctor::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class EyadaDatabase : RoomDatabase() {
    abstract fun eyadaDao(): EyadaDao
}