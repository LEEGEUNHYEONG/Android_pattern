package com.lgh.lifecycle.Timer

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.SystemClock
import java.util.*

open class LiveDataTimerViewModel : ViewModel()
{
    val ONE_SECOND : Long  = 1000

    val elapsedTime = MutableLiveData<Long>()

    var initialTime : Long

    init
    {
        initialTime = SystemClock.elapsedRealtime()
        val timer = Timer()

        timer.scheduleAtFixedRate( object:TimerTask(){
            override fun run()
            {
                val newValue = (SystemClock.elapsedRealtime() - initialTime) / 1000
                elapsedTime.postValue(newValue)
            }
        }, ONE_SECOND, ONE_SECOND)
    }
}