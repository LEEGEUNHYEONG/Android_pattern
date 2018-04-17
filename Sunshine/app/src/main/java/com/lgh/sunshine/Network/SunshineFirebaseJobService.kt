package com.lgh.sunshine.Network



import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.lgh.sunshine.Utlity.InjectorUtils

class SunshineFirebaseJobService : JobService()
{
    override fun onStartJob(params: JobParameters?): Boolean
    {
        val networkDataSource = InjectorUtils.provideNetworkDataSource(this.applicationContext)
        networkDataSource.fetchWeather()

        jobFinished(params!!, false)

        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean
    {
        return true
    }
}