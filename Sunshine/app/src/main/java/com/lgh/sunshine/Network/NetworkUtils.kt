package com.lgh.sunshine.Network

import android.net.Uri
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class NetworkUtils
{
    companion object
    {
        private val DYNAMIC_WEATHER_URL = "https://andfun-weather.udacity.com/weather"
        private val STATIC_WEATHER_URL = "https://andfun-weather.udacity.com/staticweather"
        private val FORECAST_BASE_URL = DYNAMIC_WEATHER_URL

        private val format = "json"
        private val units = "metric"

        private val QUERY_PARAM = "q"
        private val FORMAT_PARAM = "mode"
        private val UNITS_PARAM = "units"
        private val DAYS_PARAM = "cnt"

        fun getUrl(): URL?
        {
            val locationQuery = "Mountain View, CA"
            return buildUrlWithLocationQuery(locationQuery)
        }

        private fun buildUrlWithLocationQuery(locationQuery: String): URL?
        {
            val weatherQueryUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, locationQuery)
                    .appendQueryParameter(FORMAT_PARAM, format)
                    .appendQueryParameter(UNITS_PARAM, units)
                    .appendQueryParameter(DAYS_PARAM, WeatherNetworkDataSource.NUM_DAYS.toString())
                    .build()

            Log.e(javaClass.simpleName, "buildUrlWithLocationQuery : ${weatherQueryUri.toString()} ::: $weatherQueryUri")

            try
            {
                val weatherQueryUrl : URL = URL(weatherQueryUri.toString())
                Log.e(javaClass.simpleName, "weatherQueryUrl : ${weatherQueryUrl})")
                return weatherQueryUrl
            }
            catch (e : MalformedURLException)
            {
                Log.e(javaClass.simpleName, "weatherQueryUrl : ${e})")
                return null
            }
        }

        @Throws(IOException::class)
        fun getResponseFromHttpUrl(url : URL ) : String
        {
            val urlConnection = url.openConnection() as HttpURLConnection

            try
            {
                val inputStream = urlConnection.inputStream

                val scanner = Scanner(inputStream)
                scanner.useDelimiter("\\A")

                val hasInput = scanner.hasNext()
                var response : String = ""

                if(hasInput)
                {
                    response = scanner.next()
                }
                scanner.close()
                return response
            }
            finally
            {

            }
        }
    }
}