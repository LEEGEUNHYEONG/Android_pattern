package com.lgh.lifecycle.Location

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.lgh.lifecycle.R


class LocationActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_PERMISSION_CODE)
        }
        else
        {
            bindLocationListener()
        }
    }

    private val REQUEST_LOCATION_PERMISSION_CODE = 1

    private val mGpsListener = MyLocationListener()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
        {
            bindLocationListener()
        }
        else
        {
            Toast.makeText(this, "Location access", Toast.LENGTH_LONG).show()
        }
    }

    private fun bindLocationListener()
    {
        BoundLocationManager.bindLocationListenerIn(this, mGpsListener, applicationContext)
    }

    private inner class MyLocationListener : LocationListener
    {
        override fun onLocationChanged(location: Location)
        {
            val textView : TextView = findViewById(R.id.location)
            textView.text = location.latitude.toString() + ", " + location.longitude
            Log.i(javaClass.simpleName, "onLocationChanged : $location")
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle)
        {
        }

        override fun onProviderEnabled(provider: String)
        {
            Toast.makeText(this@LocationActivity, "Provider enabled: $provider", Toast.LENGTH_SHORT).show()
        }

        override fun onProviderDisabled(provider: String)
        {
        }
    }
}
