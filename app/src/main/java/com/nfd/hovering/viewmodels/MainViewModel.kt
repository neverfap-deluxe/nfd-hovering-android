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

    var duration = MutableLiveData<Int>().apply { postValue(10)}

    var isFreeMode = MutableLiveData<Boolean>().apply { postValue(false)}
    var hasReminders = MutableLiveData<Boolean>().apply { postValue(true)}

    fun toggleFreeMode(freeModeVal: Boolean) {
        isFreeMode.postValue(freeModeVal)
    }

    fun toggleReminders(remindersVal: Boolean) {
        hasReminders.postValue(remindersVal)
    }

    fun setDuration(durationVal: Int) {
        duration.postValue(durationVal)
    }

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