package com.dragonslotos.vk_test.domain.usecases

import android.util.Log
import com.dragonslotos.vk_test.domain.model.CalendarTime
import java.util.Calendar
import javax.inject.Inject

class GetCalendarTime @Inject constructor() {
     public fun getTime(): CalendarTime{
         var hour: Float = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toFloat()
         Log.d("Hour", hour.toString())
         var minute: Float = Calendar.getInstance().get(Calendar.MINUTE).toFloat()
         var second: Float = Calendar.getInstance().get(Calendar.SECOND).toFloat()
         var day: String = when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
             Calendar.SUNDAY -> "SUNDAY"
             Calendar.MONDAY -> "MONDAY"
             Calendar.TUESDAY -> "TUESDAY"
             Calendar.WEDNESDAY -> "WEDNESDAY"
             Calendar.THURSDAY -> "THURSDAY"
             Calendar.FRIDAY -> "FRIDAY"
             Calendar.SATURDAY -> "SATURDAY"
             else -> "Неверный день недели"
         }
         var day_number: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
         var month = when (Calendar.getInstance().get(Calendar.MONTH)) {
             Calendar.JANUARY -> "JANUARY"
             Calendar.FEBRUARY -> "FEBRUARY"
             Calendar.MARCH -> "MARCH"
             Calendar.APRIL -> "APRIL"
             Calendar.MAY -> "MAY"
             Calendar.JUNE -> "JUNE"
             Calendar.JULY -> "JULY"
             Calendar.AUGUST -> "AUGUST"
             Calendar.SEPTEMBER -> "SEPTEMBER"
             Calendar.OCTOBER -> "OCTOBER"
             Calendar.NOVEMBER -> "NOVEMBER"
             Calendar.DECEMBER -> "DECEMBER"
             else -> "Неверный месяц"
         }
         return CalendarTime(hour, minute, second, day, month, day_number)
     }
}