package io.github.anderscheow.library.views

import android.app.Activity
import android.content.Context
import android.util.AttributeSet

import java.util.Timer
import java.util.TimerTask

class LoadingTextView : android.support.v7.widget.AppCompatTextView {

    private var defaultText = EMPTY
    private var currentDot = ONE_DOT

    private val timer = Timer()

    private val textViewText: String
        get() = text.toString()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun start() {
        defaultText = textViewText

        try {
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    animateText()
                }
            }, 0, 1000)
        } catch (ex: Exception) {
            ex.printStackTrace()
            stop()
        }

    }

    fun stop() {
        try {
            timer.cancel()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    private fun animateText() {
        if (context is Activity) {
            (context as Activity).runOnUiThread {
                if (defaultText.isEmpty()) {
                    return@runOnUiThread
                }

                animateLoading(defaultText)
            }
        } else {
            timer.cancel()
        }
    }

    private fun animateLoading(text: String) {
        when (currentDot) {
            ONE_DOT -> setText(generateText(text, TWO_DOT))
            TWO_DOT -> setText(generateText(text, THREE_DOT))
            THREE_DOT -> setText(generateText(text, ONE_DOT))
        }
    }

    private fun generateText(text: String, newAppend: String): String {
        currentDot = newAppend
        return text + currentDot
    }

    companion object {

        private val EMPTY = ""
        private val ONE_DOT = "."
        private val TWO_DOT = ".."
        private val THREE_DOT = "..."
    }
}