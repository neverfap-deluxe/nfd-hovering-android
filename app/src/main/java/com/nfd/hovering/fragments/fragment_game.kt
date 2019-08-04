package com.nfd.hovering.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nfd.hovering.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_game.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.util.Log
import androidx.core.view.ViewCompat.setScaleY
import androidx.core.view.ViewCompat.setScaleX
import kotlinx.android.synthetic.main.fragment_game.*
import androidx.lifecycle.Observer
import java.util.*


class FreeFragment : Fragment() {
    private val model : MainViewModel by viewModel()

    // defaults
    var defaultInitialScaleState = 1
    var defaultEndScaleState = 2
    var defaultScaleRate = 0.0001f

    val defaultTimer = 0

    val defaultIncreasingScale = true
    val defaultFadeTimer = 0

    // references
    var timer: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.nfd.hovering.R.layout.fragment_about, container, false)
        val context = context

        hover_button.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Pressed down
                    startHovering()
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    // Released
                    endHovering()
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_CANCEL -> {
                    // Released - Dragged finger outside
                    endHovering()
                    return@OnTouchListener true
                }
            }
            false
        })

        return view
    }

    fun startHovering() {
        model.setInitialDefaults(defaultInitialScaleState, defaultEndScaleState, defaultScaleRate, defaultTimer, defaultIncreasingScale, defaultFadeTimer)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                model.increaseTimer(timer + 1)

                scaleElement()
            }
        }, 0, 1000)


        model.timer.observe(this, Observer { timerValue ->
            timer = timerValue
            hover_text_update.text = ""+timerValue
        })
    }

    fun scaleElement() {
        val scalingFactor = 0.5f

        view.scaleY = scalingFactor
        view.scaleX = scalingFactor
    }

    fun endHovering() {

    }


    companion object {
        fun newInstance(): FreeFragment = FreeFragment()
    }
}

