package io.github.anderscheow.library.views

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent

@Suppress("UNUSED")
class NonSwipeableViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private val enabled: Boolean = false

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return this.enabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return this.enabled && super.onInterceptTouchEvent(event)
    }

    override fun executeKeyEvent(event: KeyEvent): Boolean {
        return this.enabled && super.executeKeyEvent(event)
    }
}