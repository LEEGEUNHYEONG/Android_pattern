package com.lgh.sunshine.Utlity

import android.content.Context
import android.text.format.DateUtils
import com.lgh.sunshine.R

import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object SunshineDateUtils
{
    /* Milliseconds in a day */
    val DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1)

    val normalizedUtcMsForToday: Long
        get()
        {
            val utcNowMillis = System.currentTimeMillis()

            val currentTimeZone = TimeZone.getDefault()

            val gmtOffsetMillis = currentTimeZone.getOffset(utcNowMillis).toLong()

            val timeSinceEpochLocalTimeMillis = utcNowMillis + gmtOffsetMillis

            val daysSinceEpochLocal = TimeUnit.MILLISECONDS.toDays(timeSinceEpochLocalTimeMillis)

            return TimeUnit.DAYS.toMillis(daysSinceEpochLocal)
        }


    val normalizedUtcDateForToday: Date
        get()
        {
            val normalizedMilli = normalizedUtcMsForToday
            return Date(normalizedMilli)
        }


    private fun elapsedDaysSinceEpoch(utcDate: Long): Long
    {
        return TimeUnit.MILLISECONDS.toDays(utcDate)
    }

    private fun getLocalMidnightFromNormalizedUtcDate(normalizedUtcDate: Long): Long
    {
        val timeZone = TimeZone.getDefault()

        val gmtOffset = timeZone.getOffset(normalizedUtcDate).toLong()
        return normalizedUtcDate - gmtOffset
    }

    fun getFriendlyDateString(context: Context, normalizedUtcMidnight: Long, showFullDate: Boolean): String
    {
        val localDate = getLocalMidnightFromNormalizedUtcDate(normalizedUtcMidnight)

        val daysFromEpochToProvidedDate = elapsedDaysSinceEpoch(localDate)

        val daysFromEpochToToday = elapsedDaysSinceEpoch(System.currentTimeMillis())

        if (daysFromEpochToProvidedDate == daysFromEpochToToday || showFullDate)
        {

            val dayName = getDayName(context, localDate)
            val readableDate = getReadableDateString(context, localDate)
            if (daysFromEpochToProvidedDate - daysFromEpochToToday < 2)
            {
                val localizedDayName = SimpleDateFormat("EEEE").format(localDate)
                return readableDate.replace(localizedDayName, dayName)
            }
            else
            {
                return readableDate
            }
        }
        else if (daysFromEpochToProvidedDate < daysFromEpochToToday + 7)
        {
            return getDayName(context, localDate)
        }
        else
        {
            val flags = (DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_NO_YEAR or DateUtils.FORMAT_ABBREV_ALL or DateUtils.FORMAT_SHOW_WEEKDAY)

            return DateUtils.formatDateTime(context, localDate, flags)
        }
    }

    private fun getReadableDateString(context: Context, timeInMillis: Long): String
    {
        val flags = (DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_NO_YEAR or DateUtils.FORMAT_SHOW_WEEKDAY)

        return DateUtils.formatDateTime(context, timeInMillis, flags)
    }

    private fun getDayName(context: Context, dateInMillis: Long): String
    {

        val daysFromEpochToProvidedDate = elapsedDaysSinceEpoch(dateInMillis)
        val daysFromEpochToToday = elapsedDaysSinceEpoch(System.currentTimeMillis())

        val daysAfterToday = (daysFromEpochToProvidedDate - daysFromEpochToToday).toInt()

        when (daysAfterToday)
        {
            0    -> return context.getString(R.string.today)
            1    -> return context.getString(R.string.tomorrow)

            else ->
            {
                val dayFormat = SimpleDateFormat("EEEE")
                return dayFormat.format(dateInMillis)
            }
        }
    }

}
