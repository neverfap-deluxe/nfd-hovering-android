package com.nfd.hovering.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nfd.hovering.data.Score

class MainViewModel(val context: Context) : ViewModel() {
    val scores = MutableLiveData<MutableList<Score>>()

    val initialScaleState = MutableLiveData<Int>()
    val endScaleState = MutableLiveData<Int>()
    val scaleRate = MutableLiveData<Float>()

    val timer = MutableLiveData<Int>()
    val scale = MutableLiveData<Float>()

    val increasingScale = MutableLiveData<Boolean>()
    val fadeTimer = MutableLiveData<Int>()

    fun setInitialDefaults(initialScaleStateVal: Int, endScaleStateVal: Int, scaleRateVal: Float, timerVal: Int, increasingScaleVal: Boolean, fadeTimerVal: Int) {
        initialScaleState.postValue(initialScaleStateVal)
        endScaleState.postValue(endScaleStateVal)
        scaleRate.postValue(scaleRateVal)

        timer.postValue(timerVal)

        increasingScale.postValue(increasingScaleVal)
        fadeTimer.postValue(fadeTimerVal)
    }

    fun increaseTimer(timerValue: Int) {
        timer.postValue(timerValue)
    }

    fun increaseScale(scaleValue: Float) {
        scale.postValue(scaleValue)
    }
}