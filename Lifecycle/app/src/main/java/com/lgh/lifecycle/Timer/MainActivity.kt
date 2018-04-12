package com.lgh.lifecycle.Timer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import android.widget.TextView
import com.lgh.lifecycle.R

class MainActivity : AppCompatActivity()
{
    lateinit var liveDataTimerViewModel : LiveDataTimerViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        liveDataTimerViewModel = ViewModelProviders.of(this).get(LiveDataTimerViewModel::class.java)

        subscribe()
    }

    fun subscribe()
    {
        val elapsedTimerObserver = Observer<Long> {
            val newText = this@MainActivity.resources.getString(R.string.seconds, it)
            findViewById<TextView>(R.id.timer_textview).text = newText
            Log.d("MainActivity", "timer update")
        }

        liveDataTimerViewModel.elapsedTime.observe(this, elapsedTimerObserver)
    }

    fun setChronometer()
    {

        val chronometerViewModel = ViewModelProviders.of(this).get(ChronometerViewModel::class.java)

        val chronometer = findViewById<Chronometer>(R.id.chronometer)

        if(chronometerViewModel.startTime == null )
        {
            var startTime = SystemClock.elapsedRealtime()
            chronometerViewModel.startTime = startTime
            chronometer.base = startTime
        }
        else
        {
            chronometer.base = chronometerViewModel.startTime!!
        }

        chronometer.start()
    }
}
