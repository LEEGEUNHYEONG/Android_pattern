package com.lgh.sunshine.Activity.Detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.lgh.sunshine.Database.Entity.WeatherEntry
import com.lgh.sunshine.Database.SunshineRepository
import java.util.*

class DetailActivityViewModel(repository: SunshineRepository, date: Date) : ViewModel()
{
    var weather : LiveData<WeatherEntry>? = null

    private var date: Date = date

    private var repository: SunshineRepository = repository

    init
    {
        weather = repository.getWeatherByDate(date)
    }


}