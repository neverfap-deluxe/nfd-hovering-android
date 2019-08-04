package com.nfd.hovering.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nfd.hovering.R
import com.nfd.hovering.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TimedFragment : Fragment() {
    private val model : MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        val context = context

        return view
    }

    companion object {
        fun newInstance(): TimedFragment = TimedFragment()
    }
}

