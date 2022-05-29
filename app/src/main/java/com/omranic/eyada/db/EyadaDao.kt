package com.omranic.eyada.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omranic.eyada.model.Doctor

@Dao
interface EyadaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDoctors(doctors: List<Doctor>)

    @Query("select * from doctors")
    fun getDoctors(): LiveData<List<Doctor>>
}