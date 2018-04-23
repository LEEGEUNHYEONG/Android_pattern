package com.lgh.sunshine.Network

import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import android.util.Log
import com.firebase.jobdispatcher.*
import com.lgh.sunshine.AppExecutors
import com.lgh.sunshine.Database.Entity.WeatherEntry
import java.util.concurrent.TimeUnit


class WeatherNetworkDataSource
{
    var mDownloadedWeatherForecasts: MutableLiveData<Array<WeatherEntry?>> = MutableLiveData<Array<WeatherEntry?>>()

    companion object
    {
        val NUM_DAYS = 14

        private val SYNC_INTERVAL_HOURS = 3
        private val SYNC_INTERVAL_SECONDS = TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS.toLong())
        private val SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3

        private val LOCK = Any()
        private var instance: WeatherNetworkDataSource? = null
        private lateinit var context: Context
        private lateinit var executors: AppExecutors

        private val SUNSHINE_SYNC_TAG = "sunshine-sync"


        fun getInstance(context: Context, executors: AppExecutors): WeatherNetworkDataSource
        {
            if (instance == null)
            {
                synchronized(LOCK) {
                    instance = WeatherNetworkDataSource()
                    this.context = context
                    this.executors = executors

                }
            }
            return instance!!
        }
    }

    public fun startFetchWeatherService()
    {
        val intent = Intent(context, SunshineSyncIntentService::class.java)
        context.startService(intent)
    }

    public fun scheduleRecurringFetchWeatherSync()
    {
        val driver = GooglePlayDriver(context)
        val dispatcher = FirebaseJobDispatcher(driver)

        val syncSunshineJob = dispatcher.newJobBuilder()
                .setService(SunshineFirebaseJobService::class.java)
                .setTag(SUNSHINE_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SYNC_INTERVAL_SECONDS.toInt(), SYNC_INTERVAL_SECONDS.toInt() + SYNC_FLEXTIME_SECONDS.toInt()))
                .setReplaceCurrent(true)
                .build()
        dispatcher.schedule(syncSunshineJob)
    }

    public fun fetchWeather()
    {
        Log.i(javaClass.simpleName, "fetchWeather")

        executors.networkIO.execute({
            try
            {
                val weatherRequestUrl = NetworkUtils.getUrl()
                Log.e(javaClass.simpleName,"${weatherRequestUrl.toString()}")
                val jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl!!)

                val response: WeatherResponse? = OpenWeatherJsonParser.parse(jsonWeatherResponse)

                if(response != null && response.weatherForecast != null)
                {
                    Log.i(javaClass.simpleName, "fetchWeather : ${response.weatherForecast.size} : ${response.weatherForecast[0]!!.date} : ${response.weatherForecast[0]!!.min} : ${response.weatherForecast[0]!!.max }")
                    mDownloadedWeatherForecasts.postValue(response.weatherForecast)
                }
            }
            catch (e: Exception)
            {
                e.printStackTrace()
            }
        })
    }


}