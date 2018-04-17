package com.lgh.sunshine.Database

import android.arch.lifecycle.LiveData
import android.util.Log
import com.lgh.sunshine.AppExecutors
import com.lgh.sunshine.Database.Entity.ListWeatherEntry
import com.lgh.sunshine.Database.Entity.WeatherDao
import com.lgh.sunshine.Database.Entity.WeatherEntry
import com.lgh.sunshine.Network.WeatherNetworkDataSource
import com.lgh.sunshine.Utlity.SunshineDateUtils
import java.util.*


class SunshineRepository private constructor(weatherDao: WeatherDao, weatherNetworkDataSource: WeatherNetworkDataSource, executors: AppExecutors)
{
    private var weatherDao: WeatherDao = weatherDao
    private var weatherNetworkDataSource: WeatherNetworkDataSource = weatherNetworkDataSource
    private var executors: AppExecutors = executors

    private var mInitialized = false

    init
    {
        val networkData= weatherNetworkDataSource!!.mDownloadedWeatherForecasts
        networkData.observeForever({ it ->
            executors.diskIO.execute({
                deleteOldData()
                // todo
                //weatherDao.bulkInsert(it))
            })
        })
    }

    companion object
    {
        private val LOCK = Any()
        private var mInstance: SunshineRepository? = null




        fun getInstance(weatherDao: WeatherDao, weatherNetworkDataSource: WeatherNetworkDataSource, executors: AppExecutors): SunshineRepository
        {
            if (mInstance == null)
            {
                synchronized(LOCK) {
                    mInstance = SunshineRepository(weatherDao, weatherNetworkDataSource, executors)
                }
            }

            Log.i(javaClass.simpleName, "Create Sunshine Repository")
            return mInstance!!
        }
    }

    @Synchronized
    private fun initializeData()
    {
        if (mInitialized)
        {
            return
        }
        mInitialized = true

        weatherNetworkDataSource!!.scheduleRecurringFetchWeatherSync()

        executors.diskIO.execute({
            if (isFetchNeeded())
            {
                startFetchWeatherService()
            }
        })
    }

    fun getCurrentWeatherForecasts(): LiveData<List<ListWeatherEntry>>
    {
        initializeData()
        val today = SunshineDateUtils.normalizedUtcDateForToday
        return weatherDao.getCurrentWeatherForecasts(today)
    }

    fun getWeatherByDate(date: Date): LiveData<WeatherEntry>
    {
        initializeData()
        return weatherDao.getWeatherByDate(date)
    }

    private fun deleteOldData()
    {
        val today = SunshineDateUtils.normalizedUtcDateForToday
        weatherDao.deleteOldWeather(today)
    }

    private fun isFetchNeeded(): Boolean
    {
        val today = SunshineDateUtils.normalizedUtcDateForToday
        val count = weatherDao.countAllFutureWeather(today)
        return (count < WeatherNetworkDataSource.NUM_DAYS)
    }

    private fun startFetchWeatherService()
    {
        weatherNetworkDataSource!!.startFetchWeatherService()
    }

}