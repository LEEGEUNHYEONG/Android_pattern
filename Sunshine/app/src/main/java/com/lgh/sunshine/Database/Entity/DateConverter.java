package com.lgh.sunshine.Database.Entity;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter
{
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    /**
    @TypeConverter
    public static Date fromTimestamp(Long value)
    {
        if(value == null)
        {
            return null;
        }
        else
        {
            return new Date(value);
        }

    }

    @TypeConverter
    public static Long dateToTimestamp( Date date)
    {
        return date.getTime();
    }
    **/

}
