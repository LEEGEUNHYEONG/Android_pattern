package com.lgh.sunshine.Activity.Main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.lgh.sunshine.Database.Entity.ListWeatherEntry
import com.lgh.sunshine.Database.SunshineRepository

class MainActivityViewModel(repository: SunshineRepository) : ViewModel()
{
    var repository = repository
    var forecast : LiveData<List<ListWeatherEntry>> = repository.getCurrentWeatherForecasts()

}