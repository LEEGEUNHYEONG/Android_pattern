package com.lgh.lifecycle.Location

import android.annotation.SuppressLint
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log


open class BoundLocationManager
{
    companion object
    {
        fun bindLocationListenerIn(lifecycleOwner: LifecycleOwner, listener: LocationListener, context: Context)
        {
            BoundLocationListener(lifecycleOwner, listener, context)
        }
    }


    internal class BoundLocationListener(lifecycleOwner : LifecycleOwner, listener : LocationListener, context : Context) : LifecycleObserver
    {
        private var context : Context = context
        private var locationManager : LocationManager? = null
        private var listener : LocationListener = listener

        init
        {
            lifecycleOwner.lifecycle.addObserver(this)
        }


        @SuppressLint("MissingPermission")
        // TODO : call resume
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun addLocationListener()
        {
            // Note: Use the Fused Location Provider from Google Play Services instead.
            // https://developers.google.com/android/reference/com/google/android/gms/location/FusedLocationProviderApi

            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, listener)
            Log.d("BoundLocationManager", "Listener added")

            // Force an update with the last location, if available.
            val lastLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (lastLocation != null)
            {
                listener.onLocationChanged(lastLocation)
            }
        }

        //TODO: call pause
        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun removeLocationListener()
        {
            if (locationManager == null)
            {
                return
            }

            locationManager!!.removeUpdates(listener)
            locationManager = null
            Log.d("BoundLocationManager", "Listener removed")
        }
    }


}