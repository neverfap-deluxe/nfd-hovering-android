package com.nfd.hovering.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nfd.hovering.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_game.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_game.view.*
import java.util.*


class GameFragment : Fragment() {
    private val model : MainViewModel by viewModel()
    private lateinit var gameTimer: Timer
    private lateinit var sTimer: Timer

    // defaults
    var defaultInitialScaleState = 1
    var defaultEndScaleState = 2
    var defaultScaleRate = 0.0001f

    val defaultTimer = 0

    val defaultIncreasingScale = true
    val defaultFadeTimer = 0

    // references
    var timer: Int = 0
    var scale: Float = 1.0f
    var scaleRate: Float = defaultScaleRate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.nfd.hovering.R.layout.fragment_game, container, false)

        view.hover_button.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Pressed down
                    startHovering()
                    Log.i("nfd", "pressed down")
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    // Released
                    endHovering()
                    Log.i("nfd", "lifted up")
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_CANCEL -> {
                    // Released - Dragged finger outside
                    endHovering()
                    Log.i("nfd", "out of circle")
                    return@OnTouchListener true
                }
            }
            false
        })

        model.timer.observe(this, Observer { timerValue ->
            timer = timerValue
            hover_text_update.text = ""+timer
        })
        model.scale.observe(this, Observer { scaleValue ->
            scale = scaleValue
            hover_button.scaleY = scale
            hover_button.scaleX = scale
        })

        return view
    }

    fun startHovering() {
        model.setInitialDefaults(defaultInitialScaleState, defaultEndScaleState, defaultScaleRate, defaultTimer, defaultIncreasingScale, defaultFadeTimer)

        gameTimer = Timer()
        gameTimer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                model.increaseTimer(timer + 1)
                model.increaseScale(scale + scaleRate)
                Log.i("cake", "hey" + timer)
            }
        }, 0, 1000)
    }

    fun endHovering() {
        model.increaseTimer(0)
        model.increaseScale(defaultInitialScaleState)

        gameTimer.cancel()
    }

    companion object {
        fun newInstance(): GameFragment = GameFragment()
    }
}

