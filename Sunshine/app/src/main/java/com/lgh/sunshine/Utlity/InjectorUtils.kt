package com.lgh.sunshine.Utlity

import android.content.Context
import com.lgh.sunshine.Activity.Detail.DetailViewModelFactory
import com.lgh.sunshine.Activity.Main.MainViewModelFactory
import com.lgh.sunshine.AppExecutors
import com.lgh.sunshine.Database.SunshineDatabase
import com.lgh.sunshine.Database.SunshineRepository
import com.lgh.sunshine.Network.WeatherNetworkDataSource
import java.util.*

class InjectorUtils
{
    companion object
    {
        fun provideRepository(context : Context) : SunshineRepository
        {
            val database = SunshineDatabase.getDatabase(context.applicationContext)
            val executors = AppExecutors.getInstance()
            val networkDataSource = WeatherNetworkDataSource.getInstance(context.applicationContext, executors)

            return SunshineRepository.getInstance(database.weatherDao(), networkDataSource, executors)
        }

        fun provideNetworkDataSource(context :Context ) : WeatherNetworkDataSource
        {
            provideRepository(context.applicationContext)
            val executors = AppExecutors.getInstance()
            return WeatherNetworkDataSource.getInstance(context.applicationContext, executors)
        }

        fun provideDetailViewModelFactory(context : Context, date : Date) : DetailViewModelFactory
        {
            val repository = provideRepository(context.applicationContext)
            return DetailViewModelFactory(repository, date)
        }

        fun provideMainActivityViewmOdelFactory(context :Context) : MainViewModelFactory
        {
            val repository = provideRepository(context.applicationContext)
            return MainViewModelFactory(repository)
        }
    }
}