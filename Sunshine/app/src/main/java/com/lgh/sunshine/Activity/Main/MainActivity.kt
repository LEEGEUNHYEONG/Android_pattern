package com.lgh.sunshine.Activity.Main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.lgh.sunshine.Activity.Detail.DetailActivity
import com.lgh.sunshine.Database.Entity.WeatherEntry
import com.lgh.sunshine.R
import com.lgh.sunshine.Utlity.InjectorUtils
import java.util.*


class MainActivity : AppCompatActivity (), ForecastAdapter.ForecastAdapterOnItemClickHandler
{
    private lateinit var mForecastAdapter: ForecastAdapter
    private lateinit var mRecyclerView: RecyclerView
    private var mPosition = RecyclerView.NO_POSITION
    private lateinit var mLoadingIndicator: ProgressBar
    private lateinit var mViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        mRecyclerView = findViewById(R.id.recyclerview_forecast)

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator)

        val layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.setHasFixedSize(true)

        mForecastAdapter = ForecastAdapter(this, this)

        mRecyclerView.adapter = mForecastAdapter

        val factory = InjectorUtils.provideMainActivityViewModelFactory(this.applicationContext)
        mViewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        mViewModel.forecast.observe(this, android.arch.lifecycle.Observer {
          
            mForecastAdapter.swapForecast(it!!.toList())

            if(mPosition == RecyclerView.NO_POSITION)
            {
                mPosition = 0
            }

            mRecyclerView.smoothScrollToPosition(mPosition)

            Log.i(javaClass.simpleName, "observe : ${it.size}")

            if(it != null && it.size != 0)
            {
                showWeatherDataView()
            }
            else
            {
                showLoading()
            }
        })
    }

    override fun onItemClick(date: Date)
    {
        val weatherDetailIntent = Intent(this@MainActivity, DetailActivity::class.java)
        val timestamp = date.time
        weatherDetailIntent.putExtra(DetailActivity.WEATHER_ID_EXTRA, timestamp)
        startActivity(weatherDetailIntent)
    }

    fun showWeatherDataView()
    {
        mRecyclerView.visibility = View.VISIBLE
        mLoadingIndicator.visibility = View.INVISIBLE
    }

    fun showLoading()
    {
        mRecyclerView.visibility = View.INVISIBLE
        mLoadingIndicator.visibility = View.VISIBLE
    }
}