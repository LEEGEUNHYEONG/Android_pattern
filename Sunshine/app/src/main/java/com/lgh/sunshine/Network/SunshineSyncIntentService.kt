package com.lgh.sunshine.Network

import android.app.IntentService
import android.content.Intent
import com.lgh.sunshine.Utlity.InjectorUtils

class SunshineSyncIntentService : IntentService("SunshineSyncIntentService")
{
    override fun onHandleIntent(intent: Intent?)
    {
        val networkDataSource = InjectorUtils.provideNetworkDataSource(this.applicationContext)
        networkDataSource.fetchWeather()
    }
}