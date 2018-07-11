package io.github.anderscheow.library.views

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent

@Suppress("UNUSED")
class NonSwipeableViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    override fun isEnabled(): Boolean {
        return false
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return isEnabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return isEnabled && super.onInterceptTouchEvent(event)
    }

    override fun executeKeyEvent(event: KeyEvent): Boolean {
        return isEnabled && super.executeKeyEvent(event)
    }
}