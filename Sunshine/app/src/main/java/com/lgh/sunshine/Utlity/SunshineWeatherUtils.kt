package com.lgh.sunshine.Utlity


import android.content.Context
import android.util.Log
import com.lgh.sunshine.R


object SunshineWeatherUtils
{
    fun formatTemperature(context: Context, temperature: Double): String
    {
        val temperatureFormatResourceId = R.string.format_temperature

        /* For presentation, assume the user doesn't care about tenths of a degree. */
        return String.format(context.getString(temperatureFormatResourceId), temperature)
    }

    fun getFormattedWind(context: Context, windSpeed: Double, degrees: Double): String
    {
        val windFormat = R.string.format_wind_kmh

        var direction = "Unknown"
        if (degrees >= 337.5 || degrees < 22.5)
        {
            direction = "N"
        }
        else if (degrees >= 22.5 && degrees < 67.5)
        {
            direction = "NE"
        }
        else if (degrees >= 67.5 && degrees < 112.5)
        {
            direction = "E"
        }
        else if (degrees >= 112.5 && degrees < 157.5)
        {
            direction = "SE"
        }
        else if (degrees >= 157.5 && degrees < 202.5)
        {
            direction = "S"
        }
        else if (degrees >= 202.5 && degrees < 247.5)
        {
            direction = "SW"
        }
        else if (degrees >= 247.5 && degrees < 292.5)
        {
            direction = "W"
        }
        else if (degrees >= 292.5 && degrees < 337.5)
        {
            direction = "NW"
        }

        return String.format(context.getString(windFormat), windSpeed, direction)
    }

    fun getStringForWeatherCondition(context: Context, weatherId: Int): String
    {
        val stringId: Int
        if (weatherId >= 200 && weatherId <= 232)
        {
            stringId = R.string.condition_2xx
        }
        else if (weatherId >= 300 && weatherId <= 321)
        {
            stringId = R.string.condition_3xx
        }
        else when (weatherId)
        {
            500  -> stringId = R.string.condition_500
            501  -> stringId = R.string.condition_501
            502  -> stringId = R.string.condition_502
            503  -> stringId = R.string.condition_503
            504  -> stringId = R.string.condition_504
            511  -> stringId = R.string.condition_511
            520  -> stringId = R.string.condition_520
            531  -> stringId = R.string.condition_531
            600  -> stringId = R.string.condition_600
            601  -> stringId = R.string.condition_601
            602  -> stringId = R.string.condition_602
            611  -> stringId = R.string.condition_611
            612  -> stringId = R.string.condition_612
            615  -> stringId = R.string.condition_615
            616  -> stringId = R.string.condition_616
            620  -> stringId = R.string.condition_620
            621  -> stringId = R.string.condition_621
            622  -> stringId = R.string.condition_622
            701  -> stringId = R.string.condition_701
            711  -> stringId = R.string.condition_711
            721  -> stringId = R.string.condition_721
            731  -> stringId = R.string.condition_731
            741  -> stringId = R.string.condition_741
            751  -> stringId = R.string.condition_751
            761  -> stringId = R.string.condition_761
            762  -> stringId = R.string.condition_762
            771  -> stringId = R.string.condition_771
            781  -> stringId = R.string.condition_781
            800  -> stringId = R.string.condition_800
            801  -> stringId = R.string.condition_801
            802  -> stringId = R.string.condition_802
            803  -> stringId = R.string.condition_803
            804  -> stringId = R.string.condition_804
            900  -> stringId = R.string.condition_900
            901  -> stringId = R.string.condition_901
            902  -> stringId = R.string.condition_902
            903  -> stringId = R.string.condition_903
            904  -> stringId = R.string.condition_904
            905  -> stringId = R.string.condition_905
            906  -> stringId = R.string.condition_906
            951  -> stringId = R.string.condition_951
            952  -> stringId = R.string.condition_952
            953  -> stringId = R.string.condition_953
            954  -> stringId = R.string.condition_954
            955  -> stringId = R.string.condition_955
            956  -> stringId = R.string.condition_956
            957  -> stringId = R.string.condition_957
            958  -> stringId = R.string.condition_958
            959  -> stringId = R.string.condition_959
            960  -> stringId = R.string.condition_960
            961  -> stringId = R.string.condition_961
            962  -> stringId = R.string.condition_962
            else -> return context.getString(R.string.condition_unknown, weatherId)
        }

        return context.getString(stringId)
    }

    fun getSmallArtResourceIdForWeatherCondition(weatherId: Int): Int
    {

        /*
         * Based on weather code data for Open Weather Map.
         */
        if (weatherId >= 200 && weatherId <= 232)
        {
            return R.drawable.ic_storm
        }
        else if (weatherId >= 300 && weatherId <= 321)
        {
            return R.drawable.ic_light_rain
        }
        else if (weatherId >= 500 && weatherId <= 504)
        {
            return R.drawable.ic_rain
        }
        else if (weatherId == 511)
        {
            return R.drawable.ic_snow
        }
        else if (weatherId >= 520 && weatherId <= 531)
        {
            return R.drawable.ic_rain
        }
        else if (weatherId >= 600 && weatherId <= 622)
        {
            return R.drawable.ic_snow
        }
        else if (weatherId >= 701 && weatherId <= 761)
        {
            return R.drawable.ic_fog
        }
        else if (weatherId == 761 || weatherId == 771 || weatherId == 781)
        {
            return R.drawable.ic_storm
        }
        else if (weatherId == 800)
        {
            return R.drawable.ic_clear
        }
        else if (weatherId == 801)
        {
            return R.drawable.ic_light_clouds
        }
        else if (weatherId >= 802 && weatherId <= 804)
        {
            return R.drawable.ic_cloudy
        }
        else if (weatherId >= 900 && weatherId <= 906)
        {
            return R.drawable.ic_storm
        }
        else if (weatherId >= 958 && weatherId <= 962)
        {
            return R.drawable.ic_storm
        }
        else if (weatherId >= 951 && weatherId <= 957)
        {
            return R.drawable.ic_clear
        }

        Log.e(javaClass.simpleName, "Unknown Weather: $weatherId")
        return R.drawable.ic_storm
    }


    fun getLargeArtResourceIdForWeatherCondition(weatherId: Int): Int
    {

        /*
         * Based on weather code data for Open Weather Map.
         */
        if (weatherId >= 200 && weatherId <= 232)
        {
            return R.drawable.art_storm
        }
        else if (weatherId >= 300 && weatherId <= 321)
        {
            return R.drawable.art_light_rain
        }
        else if (weatherId >= 500 && weatherId <= 504)
        {
            return R.drawable.art_rain
        }
        else if (weatherId == 511)
        {
            return R.drawable.art_snow
        }
        else if (weatherId >= 520 && weatherId <= 531)
        {
            return R.drawable.art_rain
        }
        else if (weatherId >= 600 && weatherId <= 622)
        {
            return R.drawable.art_snow
        }
        else if (weatherId >= 701 && weatherId <= 761)
        {
            return R.drawable.art_fog
        }
        else if (weatherId == 761 || weatherId == 771 || weatherId == 781)
        {
            return R.drawable.art_storm
        }
        else if (weatherId == 800)
        {
            return R.drawable.art_clear
        }
        else if (weatherId == 801)
        {
            return R.drawable.art_light_clouds
        }
        else if (weatherId >= 802 && weatherId <= 804)
        {
            return R.drawable.art_clouds
        }
        else if (weatherId >= 900 && weatherId <= 906)
        {
            return R.drawable.art_storm
        }
        else if (weatherId >= 958 && weatherId <= 962)
        {
            return R.drawable.art_storm
        }
        else if (weatherId >= 951 && weatherId <= 957)
        {
            return R.drawable.art_clear
        }

        Log.e(javaClass.simpleName, "Unknown Weather: $weatherId")
        return R.drawable.art_storm
    }
}