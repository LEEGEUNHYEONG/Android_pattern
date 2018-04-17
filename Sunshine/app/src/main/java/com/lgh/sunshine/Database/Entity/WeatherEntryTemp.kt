package com.lgh.sunshine.Database.Entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.*

//@Entity(tableName = "weather", indices = [Index(value = arrayOf("date"), unique = true)])
@Entity(tableName = "weather", indices = arrayOf(Index(value = *arrayOf("date"), unique = true)))
class WeatherEntryTemp
{
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    var weatherIconId : Int = 0

    var date : Date? = null

    var min : Double = 0.toDouble()
    var max : Double = 0.toDouble()
    var humidity : Double = 0.toDouble()
    var pressure : Double = 0.toDouble()
    var wind : Double = 0.toDouble()
    var degrees : Double = 0.toDouble()

    constructor(id : Int, weatherIconId : Int, date : Date, min : Double, max : Double, humidity : Double, pressure : Double, wind : Double, degrees : Double )
    {
        this.id = id
        this.weatherIconId = weatherIconId
        this.date = date
        this.min = min
        this.max = max
        this.humidity = humidity
        this.pressure = pressure
        this.wind = wind
        this.degrees = degrees
    }

    @Ignore
    constructor(weatherIconId: Int, date: Date, min: Double, max: Double, humidity: Double, pressure: Double, wind: Double, degrees: Double)
    {
        this.weatherIconId = weatherIconId
        this.date = date
        this.min = min
        this.max = max
        this.humidity = humidity
        this.pressure = pressure
        this.wind = wind
        this.degrees = degrees
    }

    constructor()
}