package com.fixess.fourmoney.tools

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class MillisecondsToDateConverter {
    @RequiresApi(Build.VERSION_CODES.O)
    fun convert(milliSeconds: Long?): LocalDate {
        val calendar: Calendar = Calendar.getInstance()
        if (milliSeconds != null) {
            calendar.timeInMillis = milliSeconds
        }else{
            calendar.timeInMillis = 0
        }
        return calendar.time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }
}