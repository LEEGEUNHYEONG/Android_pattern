package com.lgh.sunshine.Activity.Detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lgh.sunshine.Database.Entity.WeatherEntry
import com.lgh.sunshine.R
import com.lgh.sunshine.Utlity.InjectorUtils
import com.lgh.sunshine.Utlity.SunshineDateUtils
import com.lgh.sunshine.Utlity.SunshineWeatherUtils
import com.lgh.sunshine.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.extra_weather_details.view.*
import kotlinx.android.synthetic.main.primary_weather_info.view.*
import java.util.*


class DetailActivity : AppCompatActivity()
{
    lateinit var viewModel : DetailActivityViewModel

    companion object
    {
        val WEATHER_ID_EXTRA = "WEATHER_ID_EXTRA"
    }

    private val mViewModel: DetailActivityViewModel? = null

    private var detailBinding : ActivityDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_detail)

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val timestamp = intent.getLongExtra(WEATHER_ID_EXTRA, -1)
        val date = Date(timestamp)

        val factory : DetailViewModelFactory = InjectorUtils.provideDetailViewModelFactory(this.applicationContext, date)

        viewModel = ViewModelProviders.of(this, factory).get(DetailActivityViewModel::class.java)

        viewModel.weather!!.observe(this, Observer {
            if(it != null)
            {
                bindWeatherToUI(it)
            }
        })
    }

    fun bindWeatherToUI(weatherEntry: WeatherEntry)
    {
        val weatherId = weatherEntry.weatherIconId
        val weatherImageId = SunshineWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherId)

        primary_info.weather_icon.setImageResource(weatherImageId)

        val localDateMidnightGmt = weatherEntry.date!!.time
        val dateText = SunshineDateUtils.getFriendlyDateString(this@DetailActivity, localDateMidnightGmt, true)
        primary_info.date.text = dateText

        val description = SunshineWeatherUtils.getStringForWeatherCondition(this@DetailActivity, weatherId)
        val descriptionA11y = getString(R.string.a11y_forecast, description)

        primary_info.weather_description.text = description
        primary_info.weather_description.contentDescription = descriptionA11y

        val maxInCelsius = weatherEntry.max
        val highString = SunshineWeatherUtils.formatTemperature(this@DetailActivity, maxInCelsius)
        val highA11y = getString(R.string.a11y_high_temp, highString)

        primary_info.high_temperature.text = highString
        primary_info.high_temperature.contentDescription = highA11y

        val minInCelsius = weatherEntry.min
        val lowString = SunshineWeatherUtils.formatTemperature(this@DetailActivity, minInCelsius)
        val lowA11y = getString(R.string.a11y_low_temp, lowString)

        primary_info.low_temperature.text = lowString
        primary_info.low_temperature.contentDescription = lowA11y

        val humidity = weatherEntry.humidity
        val humidityString = getString(R.string.format_humidity, humidity)
        val humidityA11y = getString(R.string.a11y_humidity, humidityString)

        extra_details.humidity.text = humidityString
        extra_details.humidity.contentDescription = humidityA11y
        extra_details.humidity_label.contentDescription = humidityA11y

        val windSpeed = weatherEntry.wind
        val windDirection = weatherEntry.degrees
        val windString = SunshineWeatherUtils.getFormattedWind(this@DetailActivity, windSpeed, windDirection)
        val windA11y = getString(R.string.a11y_wind, windString)

        extra_details.wind_measurement.text = windString
        extra_details.wind_measurement.contentDescription = windA11y
        extra_details.wind_label.contentDescription = windA11y

        val pressure = weatherEntry.pressure

        val pressureString = getString(R.string.format_pressure, pressure)
        val pressureA11y = getString(R.string.a11y_pressure, pressureString)

        extra_details.pressure.text = pressureString
        extra_details.pressure.contentDescription = pressureA11y
        extra_details.pressure_label.contentDescription = pressureA11y
    }
}
