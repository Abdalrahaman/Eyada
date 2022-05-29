package com.omranic.eyada.di

import android.content.Context
import androidx.room.Room
import com.omranic.eyada.db.EyadaDao
import com.omranic.eyada.db.EyadaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDB(@ApplicationContext context: Context): EyadaDatabase {
        return Room.databaseBuilder(context, EyadaDatabase::class.java, "eyada_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(eyadaDatabase: EyadaDatabase): EyadaDao = eyadaDatabase.eyadaDao()
}