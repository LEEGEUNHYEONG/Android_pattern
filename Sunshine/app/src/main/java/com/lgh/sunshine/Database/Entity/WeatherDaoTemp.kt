package com.lgh.sunshine.Database.Entity

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import java.util.*

@Dao
interface WeatherDaoTemp
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(vararg weather: WeatherEntry)

    @Query("SELECT * FROM weather WHERE date = :date")
    fun getWeatherByDate(date: Date) : LiveData<WeatherEntry>

    @Query("SELECT id, weatherIconId, date, min, max FROM weather WHERE date >= :date")
    fun getCurrentWeatherForecasts(date : Date) : LiveData<List<ListWeatherEntry>>

    @Query("SELECT COUNT(id) FROM weather WHERE date >= :date")
    fun countAllFutureWeather(date : Date) : Int

    @Query("DELETE FROM weather WHERE date < :date")
    fun deleteOldWeather(date : Date)
}