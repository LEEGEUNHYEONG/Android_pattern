package com.lgh.sunshine.Activity.Main

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lgh.sunshine.Database.SunshineRepository

class MainViewModelFactory(repository : SunshineRepository) : ViewModelProvider.NewInstanceFactory()
{
    var repository = repository

    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return MainActivityViewModel(repository) as T
    }
}