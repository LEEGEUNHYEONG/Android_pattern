package com.lgh.sunshine.Network

import com.lgh.sunshine.Database.Entity.WeatherEntry

class WeatherResponse(weatherForecast: Array<WeatherEntry?>)
{
    var weatherForecast = weatherForecast
}
