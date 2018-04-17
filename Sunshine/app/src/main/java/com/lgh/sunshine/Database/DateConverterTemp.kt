package com.lgh.sunshine.Database

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverterTemp
{
    //  Example 예제 그대로인 경우 동작 안함
    //  아래와 같이 수정 필요
    @TypeConverter
    fun toDate (timestamp : Long) : Date?
    {
        return if(timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date : Date) : Long?
    {
        return if(date == null) null else date.time
    }


    @TypeConverter
    fun fromTimestamp(value: Long?): Date?
    {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long?
    {
        return date?.time
    }

}



