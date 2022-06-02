package com.omranic.eyada.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omranic.eyada.model.Ad
import com.omranic.eyada.model.AvailableDoctor
import com.omranic.eyada.model.Doctor

@Dao
interface EyadaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAds(ads: List<Ad>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAvailableDoctors(doctors: List<AvailableDoctor>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDoctors(doctors: List<Doctor>)


    @Query("select * from ads")
    fun getAds(): List<Ad>

    @Query("select * from available_doctors")
    fun getAvailableDoctors(): List<AvailableDoctor>

    @Query("select * from doctors")
    fun getDoctors(): LiveData<List<Doctor>>
}