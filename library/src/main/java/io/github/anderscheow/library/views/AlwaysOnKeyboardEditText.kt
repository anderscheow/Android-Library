package io.github.anderscheow.library.views

import android.content.Context
import android.support.design.widget.TextInputEditText
import android.util.AttributeSet
import android.view.KeyEvent

class AlwaysOnKeyboardEditText : TextInputEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        return true
    }
}