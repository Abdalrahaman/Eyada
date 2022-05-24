package com.omranic.eyada.controller

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.omranic.eyada.model.Pick
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class EyadaDatePicker {
    private val currentDate: LocalDate = LocalDate.now()

    fun getWeekFromNow(): List<Pick>{
        val pickers = mutableListOf<Pick>()
        var nextDate = currentDate
            for (index in 0..6) {
                pickers.add(
                    Pick(
                        nextDate.dayOfWeek.name,
                        nextDate.dayOfMonth,
                        nextDate.month.value,
                        nextDate.year
                    )
                )
                nextDate = nextDate.plusDays(1)
            }
        return pickers
    }
}