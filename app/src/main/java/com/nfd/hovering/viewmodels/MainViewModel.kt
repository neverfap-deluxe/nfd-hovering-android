package com.nfd.hovering.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nfd.hovering.data.Score

class MainViewModel(val context: Context) : ViewModel() {
    val scores = MutableLiveData<MutableList<Score>>()

//    val initialScaleState = MutableLiveData<Float>().apply { postValue(1.0f)}
//    val endScaleState = MutableLiveData<Float>().apply { postValue(2.0f)}
//    val scaleRate = MutableLiveData<Float>().apply { postValue(0.0003f)}

    val timer = MutableLiveData<Int>().apply { postValue(0)}
    val scale = MutableLiveData<Float>().apply { postValue(1.0f)}
    val fadeTimer = MutableLiveData<Int>().apply { postValue(0)}

//    val increasingScale = MutableLiveData<Boolean>().apply { postValue(true)}

    var duration: Int = 10

//    fun initSettings(durationVal: Any) {
//        durationVal = duration;
//    }

    fun increaseTimer(timerValue: Int) {
        timer.postValue(timerValue)
    }

    fun setScale(scaleValue: Float) {
        scale.postValue(scaleValue)
    }

    fun setFadeTimer(fadeTimerVal: Int) {
        fadeTimer.postValue(fadeTimerVal)
    }
}