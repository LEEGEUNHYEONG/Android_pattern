package com.lgh.sunshine.Activity.Detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lgh.sunshine.Database.SunshineRepository
import java.util.*

class DetailViewModelFactory(repository : SunshineRepository, date : Date) : ViewModelProvider.NewInstanceFactory()
{
    private var repository = repository
    private var date = date

    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return DetailActivityViewModel(repository, date ) as T

    }
}