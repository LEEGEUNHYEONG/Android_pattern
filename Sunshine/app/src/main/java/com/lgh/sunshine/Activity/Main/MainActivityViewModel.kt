package com.lgh.sunshine.Activity.Main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.lgh.sunshine.Database.Entity.ListWeatherEntry
import com.lgh.sunshine.Database.SunshineRepository

class MainActivityViewModel(repository: SunshineRepository) : ViewModel()
{
    private val mRepository:SunshineRepository = repository

    val forecast:LiveData<List<ListWeatherEntry>>

    init
    {
        forecast = mRepository.getCurrentWeatherForecasts()
    }
}