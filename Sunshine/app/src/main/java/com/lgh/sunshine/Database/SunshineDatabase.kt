package com.lgh.sunshine.Database

import android.arch.persistence.room.*
import android.content.Context
import com.lgh.sunshine.Database.Entity.DateConverter
import com.lgh.sunshine.Database.Entity.WeatherDao
import com.lgh.sunshine.Database.Entity.WeatherEntry

@Database(entities = [(WeatherEntry::class)], version = 1)
@TypeConverters(DateConverter::class)
abstract class SunshineDatabase : RoomDatabase()
{
    abstract fun weatherDao() : WeatherDao

    companion object
    {
        private val DATABASE_NAME = "weather"
        private val LOCK = Any()

        @Volatile private var instance : SunshineDatabase? = null

        internal fun getDatabase(context : Context) : SunshineDatabase
        {
            if(instance == null)
            {
                synchronized(LOCK)
                {
                    if(instance == null)
                    {
                        instance = Room.databaseBuilder(context.applicationContext,
                                SunshineDatabase::class.java, SunshineDatabase.DATABASE_NAME)
                                .build()
                    }
                }
            }
            return instance!!
        }

    }
}