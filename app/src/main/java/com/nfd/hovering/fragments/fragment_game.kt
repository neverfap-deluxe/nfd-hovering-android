package com.nfd.hovering.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import com.nfd.hovering.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_game.*
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_game.view.*
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.nfd.hovering.Utilities.Helpers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class GameFragment : Fragment() {
    private val model : MainViewModel by sharedViewModel()
    private lateinit var gameTimer: Timer
    private lateinit var scaleTimer: Timer

    // references
    var timer: Int = 0
    var fadeTimer: Int = 0
    var appScale: Float = 1.0f
    var scaleRate: Float = 0.0003f
    var increasingScale: Boolean = true
    var initialScaleState: Float = 1.0f
    var endScaleState: Float = 2.0f

    var duration: Int = 10
    var freeMode: Boolean = false
    var hasReminders: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(com.nfd.hovering.R.layout.fragment_game, container, false)

        view.hover_text_time_limit.text = if (duration == 1) "Timed: $duration minute" else "Timed: $duration minutes"

        model.duration.observe(this, Observer { durationValue ->
            duration = durationValue
            Log.i("nfd", "fragment_game - duration updated: $durationValue $freeMode")
            if (!freeMode) {
                view.hover_text_time_limit.text = if (durationValue == 1) "Timed: $durationValue minute" else "Timed: $durationValue minutes"
            }
        })

        model.isFreeMode.observe(this, Observer { freeModeValue ->
            freeMode = freeModeValue
            if (freeMode) {
                view.hover_text_time_limit.text = "Free mode"
            }
            Log.i("nfd", "fragment_game - freeModeValue updated: $freeModeValue")
        })

        model.hasReminders.observe(this, Observer { hasRemindersValue ->
            hasReminders = hasRemindersValue
            Log.i("nfd", "fragment_game - hasRemindersValue updated: $hasRemindersValue")
        })


        view.hover_button.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Pressed down
                    startHovering(view.hover_button)
                    Log.i("nfd", "pressed down")
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    // Released
                    endHovering(view.hover_button)
                    Log.i("nfd", "lifted up")
                    return@OnTouchListener true
                }
                MotionEvent.ACTION_CANCEL -> {
                    // Released - Dragged finger outside
                    endHovering(view.hover_button)
                    Log.i("nfd", "out of circle")
                    return@OnTouchListener true
                }
            }
            false
        })

        return view
    }

    fun startHovering(hover_button: Button) {
//        activity?.window?.decorView?.systemUiVisibility = (
//                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or WindowManager.LayoutParams.FLAG_FULLSCREEN
//                )
        (activity as AppCompatActivity).supportActionBar?.hide()
        (activity as AppCompatActivity).nav_view.visibility = View.GONE

        Helpers.fadeTextInstructions(hover_text_update)
        Helpers.fadeTextInstructions(hover_text_instructions)
        Helpers.fadeTextInstructions(hover_text_instructions_two)
        Helpers.fadeTextInstructions(hover_text_time_limit)

        hover_button.text = ""

        model.setDuration(duration)

        gameTimer = Timer()
        gameTimer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                model.increaseTimer(timer + 1)
                model.setFadeTimer(fadeTimer - 1)
                Log.i("nfd", "timer: $timer")
            }
        }, 0, 1000)

        scaleTimer = Timer()
        scaleTimer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                updateScale(appScale)
                Log.i("nfd", "scale: $appScale")
            }
        }, 0, 100)

        model.fadeTimer.observe(this, Observer { fadeTimerValue ->
            updatefadeTimer(fadeTimerValue)
        })

        (activity as AppCompatActivity).runOnUiThread(Runnable {
            // Stuff that updates the UI
            model.timer.observe(this, Observer { timerValue ->
                updateTimer(timerValue)
            })
            model.scale.observe(this, Observer { scaleValue ->
                setScale(scaleValue)
            })
            model.fadeTimer.observe(this, Observer { fadeTimerValue ->
                updatefadeTimer(fadeTimerValue)
            })
        })
    }

    fun endHovering(hover_button: Button) {
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).nav_view.visibility = View.VISIBLE

        Helpers.unfadeTextInstructions(hover_text_instructions)
        Helpers.unfadeTextInstructions(hover_text_instructions_two)
        Helpers.unfadeTextInstructions(hover_text_time_limit)

        hover_button.text = "Hover"
        model.increaseTimer(0)
        model.setScale(initialScaleState)

        model.timer.observe(this, Observer { timerValue ->
            updateTimer(timerValue)
        })
        model.scale.observe(this, Observer { scaleValue ->
            setScale(scaleValue)
        })

        scaleTimer.cancel()
        gameTimer.cancel()
    }

    fun updateScale(scaleValue: Float) {
        if (increasingScale && (appScale < endScaleState)) {
            model.setScale(scaleValue + scaleRate)
        }
        if (increasingScale && (appScale > endScaleState)) {
            increasingScale = false
        }

        if (!increasingScale && (appScale > initialScaleState)) {
            model.setScale(appScale - scaleRate)
        }
        if (!increasingScale && (appScale < initialScaleState)) {
            increasingScale = true
        }
    }

    private fun setScale(scaleValue: Float) {
        appScale = scaleValue
        hover_button.scaleY = appScale
        hover_button.scaleX = appScale
    }

    private fun updateTimer(timerValue: Int) {
        val durationMinutes = duration * 60

        if (!freeMode) {
            if (durationMinutes == timerValue) {
                endHovering(hover_button)
            }
        }

        if (hasReminders) {
            if (timer != 0 && timer % 60 == 0) {
                model.setFadeTimer(5)
                val minutes = timer / 60
                hover_text_update.text = if (minutes == 1) "$minutes minute" else "$minutes minutes"
                Helpers.unfadeTextInstructions(hover_text_update)
                Helpers.fadeTextInstructions(hover_text_update)
            }
        }

        timer = timerValue
    }

    private fun updatefadeTimer(fadeTimerValue: Int) {
        fadeTimer = fadeTimerValue
        if (fadeTimerValue === 0) {
            Helpers.fadeTextInstructions(hover_text_update)
        }
    }

    companion object {
        fun newInstance(): GameFragment = GameFragment()
    }
}
