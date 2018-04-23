package com.lgh.sunshine.Network

import android.util.Log
import com.lgh.sunshine.Database.Entity.WeatherEntry
import com.lgh.sunshine.Utlity.SunshineDateUtils
import org.jetbrains.annotations.Nullable
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.util.*

class OpenWeatherJsonParser
{
    companion object
    {
        private val OWM_LIST = "list"

        private val OWM_PRESSURE = "pressure"
        private val OWM_HUMIDITY = "humidity"
        private val OWM_WINDSPEED = "speed"
        private val OWM_WIND_DIRECTION = "deg"

        private val OWM_TEMPERATURE = "temp"

        // Max temperature for the day
        private val OWM_MAX = "max"
        private val OWM_MIN = "min"

        private val OWM_WEATHER = "weather"
        private val OWM_WEATHER_ID = "id"

        private val OWM_MESSAGE_CODE = "cod"

        @Throws(JSONException::class)
        private fun hasHttpError(forecastJson : JSONObject) : Boolean
        {
            if(forecastJson.has(OWM_MESSAGE_CODE))
            {
                val errorCode : Int = forecastJson.getInt(OWM_MESSAGE_CODE)

                return when(errorCode)
                {
                    HttpURLConnection.HTTP_OK -> false
                    HttpURLConnection.HTTP_NOT_FOUND -> true
                    else -> true

                }
            }
            return false
        }

        @Throws(JSONException::class)
        private fun fromJson( forecastJson : JSONObject ) : Array<WeatherEntry?>
        {
            Log.i(javaClass.simpleName, "fromJson")

            val jsonWeatherArray = forecastJson.getJSONArray(OWM_LIST)

            var weatherEntries = arrayOfNulls<WeatherEntry>(jsonWeatherArray.length())

            val normalizedUtcStartDay = SunshineDateUtils.normalizedUtcMsForToday

            for(i in 0 until jsonWeatherArray.length())
            {
                val dayForecast = jsonWeatherArray.getJSONObject(i)

                val dateTimeMillis = normalizedUtcStartDay + SunshineDateUtils.DAY_IN_MILLIS * i
                val weather : WeatherEntry = fromJson(dayForecast, dateTimeMillis)

                Log.i(javaClass.simpleName, weather.toString())

                weatherEntries[i] = weather
            }

            return weatherEntries
        }

        private fun fromJson(dayForecast :JSONObject, dateTimeMillis : Long) : WeatherEntry
        {
            val pressure = dayForecast.getDouble(OWM_PRESSURE)
            val humidity = dayForecast.getInt(OWM_HUMIDITY)
            val windSpeed = dayForecast.getDouble(OWM_WINDSPEED)
            val windDirection = dayForecast.getDouble(OWM_WIND_DIRECTION)

            val weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0)

            val weatherId = weatherObject.getInt(OWM_WEATHER_ID)


            val tempreratureObject = dayForecast.getJSONObject(OWM_TEMPERATURE)
            val max = tempreratureObject.getDouble(OWM_MAX)
            val min = tempreratureObject.getDouble(OWM_MIN)

            return WeatherEntry(weatherId, Date(dateTimeMillis), max, min, humidity.toDouble(), pressure, windSpeed, windDirection)
        }

        @Throws(JSONException::class)
        @Nullable
        fun parse(forecastJsonStr : String) : WeatherResponse?
        {
            Log.i(javaClass.simpleName, "parse")

            val forecastJson = JSONObject(forecastJsonStr)

            if(hasHttpError(forecastJson))
            {
                return null
            }

            val weatherForecast = fromJson(forecastJson)

            return WeatherResponse(weatherForecast)
        }
    }
}