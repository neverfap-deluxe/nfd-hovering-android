package com.nfd.hovering.Utilities

import android.view.animation.AlphaAnimation
import android.widget.TextView

class Helpers {

    companion object {
        fun fadeTextInstructions(textView: TextView) {
            val animation = AlphaAnimation(1.0f, 0.0f)
            animation.duration = 4000
            animation.fillAfter = true

            textView.startAnimation(animation)
        }

        fun unfadeTextInstructions(textView: TextView) {
            val animation = AlphaAnimation(0.0f, 1.0f)
            animation.duration = 1000

            textView.startAnimation(animation)
        }
    }
}