package com.lgh.lifecycle.Timer

import android.arch.lifecycle.ViewModel

class ChronometerViewModel : ViewModel()
{
    var startTime: Long? = null


    fun setStartTime(startTime: Long)
    {
        this.startTime = startTime
    }
}
