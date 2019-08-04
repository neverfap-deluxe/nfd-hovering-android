package com.nfd.hovering.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nfd.hovering.R
import com.nfd.hovering.viewmodels.MainViewModel
import it.sephiroth.android.library.numberpicker.doOnProgressChanged
import it.sephiroth.android.library.numberpicker.doOnStartTrackingTouch
import it.sephiroth.android.library.numberpicker.doOnStopTrackingTouch
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

// https://github.com/sephiroth74/NumberSlidingPicker?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=7489

class SettingsFragment : Fragment() {
    private val model : MainViewModel by viewModel()

    var duration: Int = 10

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val context = context

        view.durationPicker.progress = model.duration

        view.durationPicker.doOnProgressChanged { durationPickerFun, progress, formUser ->
            // progress changed
            Log.i("nfd", "duration: $progress")
            model.duration = progress
        }

        view.durationPicker.doOnStartTrackingTouch { durationPickerFun ->
            // tracking started
        }

        view.durationPicker.doOnStopTrackingTouch { durationPickerFun ->
            // tracking ended
        }

        return view
    }

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}

