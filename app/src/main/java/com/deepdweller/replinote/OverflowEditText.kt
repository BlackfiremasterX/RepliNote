package com.deepdweller.replinote

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * Фиксим баг из Android Lollipop, который при клике на иконку меню не дает ничего нажать
 * и сворачивается мгновенно, если надо - можно тут читануть -> https://issuetracker.google.com/issues/36937508
 */
class OverflowEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    var isActionModeOn = false

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (!isActionModeOn) {
            super.onWindowFocusChanged(hasWindowFocus)
        }
    }
}