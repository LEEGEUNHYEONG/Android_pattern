package com.lgh.lifecycle.SeekBar

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class SeekBarViewModel : ViewModel()
{
    val seekbarValue = MutableLiveData<Int>()
}