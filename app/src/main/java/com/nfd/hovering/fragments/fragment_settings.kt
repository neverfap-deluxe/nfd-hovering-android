package com.nfd.hovering.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nfd.hovering.R
import com.nfd.hovering.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import com.shawnlin.numberpicker.NumberPicker
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import kotlinx.android.synthetic.main.fragment_settings.view.number_picker


// https://github.com/sephiroth74/NumberSlidingPicker?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=7489

class SettingsFragment : Fragment() {
    private val model : MainViewModel by sharedViewModel()

    var duration: Int = 10

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val context = context

        view.number_picker?.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener {
            override fun onValueChange(picker: NumberPicker, oldVal: Int, newVal: Int) {
                Log.i("nfd", "fragment_settings $newVal")
                picker.setValue(newVal)
                model.setDuration(newVal)
            }
        })

        view.is_free_mode_switch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                fragment_settings_duration_sub_title.alpha = 0.0f
                view.number_picker.alpha = 0.0f
            } else {
                fragment_settings_duration_sub_title.alpha = 1.0f
                view.number_picker.alpha = 1.0f
            }

            model.toggleFreeMode(isChecked)
        }

        view.has_time_reminder_switch?.setOnCheckedChangeListener { _, isChecked ->
            model.toggleReminders(isChecked)
        }

        return view
    }

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}

