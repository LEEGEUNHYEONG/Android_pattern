package com.lgh.lifecycle.SeekBar

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.lgh.lifecycle.R

class SeekBarFragment : Fragment()
{
    lateinit var seekBar: SeekBar

    lateinit var seekBarViewModel: SeekBarViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val root = inflater!!.inflate(R.layout.fragment_seekbar, container, false)
        seekBar = root.findViewById(R.id.seekBar)

        seekBarViewModel = ViewModelProviders.of(activity!!).get(SeekBarViewModel::class.java)

        subscribeSeekBar()

        return root
    }


    private fun subscribeSeekBar()
    {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
            {
                if (fromUser)
                {
                    seekBarViewModel.seekbarValue.value = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?)
            {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?)
            {

            }
        })

        seekBarViewModel.seekbarValue.observe(activity!!, Observer<Int> { t ->
            if (t != null)
            {
                seekBar.progress = t
            }
        })
    }
}