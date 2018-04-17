package com.lgh.sunshine.Activity.Main

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lgh.sunshine.Database.Entity.ListWeatherEntry
import com.lgh.sunshine.R
import com.lgh.sunshine.Utlity.SunshineDateUtils
import com.lgh.sunshine.Utlity.SunshineWeatherUtils
import java.util.*


class ForecastAdapter(context: Context, clickHandler: ForecastAdapterOnItemClickHandler) : RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder>()
{

    private val VIEW_TYPE_TODAY = 0
    private val VIEW_TYPE_FUTURE_DAY = 1

    private lateinit var mContext: Context


    private var mClickHandler: ForecastAdapterOnItemClickHandler

    private var mUseTodayLayout: Boolean
    private var mForecast: List<ListWeatherEntry>? = null

    init
    {
        mContext = context
        mClickHandler = clickHandler
        mUseTodayLayout = mContext.getResources().getBoolean(R.bool.use_today_layout)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ForecastAdapterViewHolder
    {

        val layoutId = getLayoutIdByType(viewType)
        val view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false)
        view.isFocusable = true
        return ForecastAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastAdapterViewHolder, position: Int)
    {
        val currentWeather: ListWeatherEntry = mForecast!![position]

        val weatherIconId = currentWeather.weatherIconId
        val weatherImageResourceId = getImageResourceId(weatherIconId, position)
        holder.iconView.setImageResource(weatherImageResourceId)

        val dateInMillis = currentWeather.date.time
        val dateString = SunshineDateUtils.getFriendlyDateString(mContext, dateInMillis, false)

        holder.dateView.text = dateString

        val description = SunshineWeatherUtils.getStringForWeatherCondition(mContext, weatherIconId)
        val descriptionA11y = mContext.getString(R.string.a11y_forecast, description)

        holder.descriptionView.setText(description)
        holder.descriptionView.contentDescription = descriptionA11y

        val highInCelsius = currentWeather.max
        val highString = SunshineWeatherUtils.formatTemperature(mContext, highInCelsius)
        val highA11y = mContext.getString(R.string.a11y_high_temp, highString)

        holder.highTempView.setText(highString)
        holder.highTempView.contentDescription = highA11y

        val lowInCelsius = currentWeather.min
        val lowString = SunshineWeatherUtils.formatTemperature(mContext, lowInCelsius)
        val lowA11y = mContext.getString(R.string.a11y_low_temp, lowString)

        holder.lowTempView.setText(lowString)
        holder.lowTempView.contentDescription = lowA11y
    }


    private fun getImageResourceId(weatherIconId: Int, position: Int): Int
    {
        val viewType = getItemViewType(position)

        when (viewType)
        {

            VIEW_TYPE_TODAY      -> return SunshineWeatherUtils.getLargeArtResourceIdForWeatherCondition(weatherIconId)

            VIEW_TYPE_FUTURE_DAY -> return SunshineWeatherUtils.getSmallArtResourceIdForWeatherCondition(weatherIconId)

            else                 -> throw IllegalArgumentException("Invalid view type, value of $viewType")
        }
    }

    override fun getItemCount(): Int
    {
        return if (null == mForecast) 0 else mForecast!!.size
    }

    override fun getItemViewType(position: Int): Int
    {
        return if (mUseTodayLayout && position == 0)
        {
            VIEW_TYPE_TODAY
        }
        else
        {
            VIEW_TYPE_FUTURE_DAY
        }
    }

    fun swapForecast(newForecast: List<ListWeatherEntry>)
    {
        // If there was no forecast data, then recreate all of the list
        if (mForecast == null)
        {
            mForecast = newForecast
            notifyDataSetChanged()
        }
        else
        {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback()
            {
                override fun getOldListSize(): Int
                {
                    return mForecast!!.size
                }

                override fun getNewListSize(): Int
                {
                    return newForecast.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
                {
                    return mForecast!![oldItemPosition].id === newForecast.get(newItemPosition).id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
                {
                    val newWeather = newForecast[newItemPosition]
                    val oldWeather = mForecast!![oldItemPosition]
                    return newWeather.id === oldWeather.id && newWeather.date.equals(oldWeather.date)
                }
            })
            mForecast = newForecast
            result.dispatchUpdatesTo(this)
        }
    }

    private fun getLayoutIdByType(viewType: Int): Int
    {
        when (viewType)
        {

            VIEW_TYPE_TODAY      ->
            {
                return R.layout.list_item_forecast_today
            }

            VIEW_TYPE_FUTURE_DAY ->
            {
                return R.layout.forecast_list_item
            }

            else                 -> throw IllegalArgumentException("Invalid view type, value of $viewType")
        }
    }

    interface ForecastAdapterOnItemClickHandler
    {
        fun onItemClick(date: Date)
    }

    inner class ForecastAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener
    {
        val iconView: ImageView

        val dateView: TextView
        val descriptionView: TextView
        val highTempView: TextView
        val lowTempView: TextView

        init
        {

            iconView = view.findViewById(R.id.weather_icon)
            dateView = view.findViewById(R.id.date)
            descriptionView = view.findViewById(R.id.weather_description)
            highTempView = view.findViewById(R.id.high_temperature)
            lowTempView = view.findViewById(R.id.low_temperature)

            view.setOnClickListener(this)
        }


        override fun onClick(v: View)
        {
            val adapterPosition = adapterPosition
            val date = mForecast!![adapterPosition].date
            mClickHandler.onItemClick(date)
        }
    }
}