package io.github.anderscheow.library.views

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import java.text.DecimalFormat

@Suppress("UNUSED")
class CurrencyEditText : AppCompatEditText {

    interface OnAmountChangedListener {
        fun onAmountChanged(amount: Long)
    }

    private var listener: OnAmountChangedListener? = null
    private var currentAmountInString = ""
    private var currentAmountInLong = 0L
    private var textChangedListener = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            removeTextChangedListener(this)

            s?.let {
                if (before <= 0) {
                    // Character added
                    if (currentAmountInString.length <= maxLength) {
                        val amount = it.toString()[it.toString().length - 1]
                        currentAmountInString += amount
                    }
                } else {
                    // Character removed
                    currentAmountInString = currentAmountInString.dropLast(1)
                }

                formatAmount()
            }

            addTextChangedListener(this)
            setCursorToEnd()
        }

        override fun afterTextChanged(editable: Editable) {}
    }

    // public variables
    var maxLength = 10
    var currency = "RM"
        set(value) {
            field = value
            formatAmount()
        }
    var formatter = DecimalFormat("###,###,##0.00")

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun isSuggestionsEnabled(): Boolean {
        return false
    }

    override fun setInputType(type: Int) {
        super.setInputType(InputType.TYPE_CLASS_NUMBER)
    }

    fun setOnAmountChangedListener(listener: OnAmountChangedListener) {
        this.listener = listener
    }

    fun canPaste(): Boolean {
        return false
    }

    fun resetText() {
        removeTextChangedListener(textChangedListener)

        currentAmountInString = "0"

        formatAmount(false)

        addTextChangedListener(textChangedListener)
    }

    fun formatAmount(value: String) {
        value.toLongOrNull()?.let {
            this.currentAmountInLong = 0

            formatAmount()
        }
    }

    private fun init() {
        isCursorVisible = false
        isLongClickable = false

        onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                setCursorToEnd()
            }
        }

        addTextChangedListener(textChangedListener)

        customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
                return false
            }

            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                return false
            }

            override fun onDestroyActionMode(p0: ActionMode?) {
            }
        }

        setOnEditorActionListener { _, _, _ -> true }

        setOnClickListener {
            setCursorToEnd()
        }

        formatAmount()
        setCursorToEnd()
    }

    private fun setCursorToEnd() {
        post {
            setSelection(length())
        }
    }

    private fun formatAmount(withNotify: Boolean = true) {
        currentAmountInLong = currentAmountInString.toLongOrNull() ?: 0
        val d = currentAmountInLong / 100.0
        val formattedAmount = formatter.format(d)

        setText(StringBuilder()
                .append(currency.trim())
                .append(" ")
                .append(formattedAmount)
                .toString())

        if (withNotify) {
            listener?.onAmountChanged(currentAmountInLong)
        }
    }
}
