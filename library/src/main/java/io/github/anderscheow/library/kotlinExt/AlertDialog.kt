package io.github.anderscheow.library.kotlinExt

import android.app.Activity
import android.content.DialogInterface
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

inline fun Activity.singleSelector(
        title: CharSequence,
        items: List<CharSequence>,
        crossinline onClick: (DialogInterface, Int) -> Unit
) {
    MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setItems(Array(items.size) { i -> items[i].toString() }) { dialog, which ->
                onClick(dialog, which)
            }
            .show()
}

fun Activity.singleChoiceSelector(
        title: CharSequence,
        items: List<CharSequence>,
        checkedItem: Int,
        onClick: (DialogInterface, Int) -> Unit,
        onPositiveClick: (DialogInterface, Int) -> Unit,
        onNegativeClick: (DialogInterface, Int) -> Unit
) {
    MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setSingleChoiceItems(Array(items.size) { i -> items[i].toString() },
                    checkedItem) { dialog, which ->
                onClick(dialog, which)
            }
            .setPositiveButton(android.R.string.ok, onPositiveClick)
            .setNegativeButton(android.R.string.cancel, onNegativeClick)
            .show()
}

fun Activity.multiChoiceSelector(
        title: CharSequence,
        items: List<CharSequence>,
        checkedItems: List<Boolean>,
        onClick: (DialogInterface, Int, Boolean) -> Unit,
        onPositiveClick: (DialogInterface, Int) -> Unit,
        onNegativeClick: (DialogInterface, Int) -> Unit
) {
    MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMultiChoiceItems(Array(items.size) { i -> items[i].toString() },
                    checkedItems.toBooleanArray()) { dialog, which, selected ->
                onClick(dialog, which, selected)
            }
            .setPositiveButton(android.R.string.ok, onPositiveClick)
            .setNegativeButton(android.R.string.cancel, onNegativeClick)
            .show()
}

inline fun Fragment.singleSelector(
        title: CharSequence,
        items: List<CharSequence>,
        crossinline onClick: (DialogInterface, Int) -> Unit
) {
    requireActivity().singleSelector(title, items, onClick)
}

fun Fragment.singleChoiceSelector(
        title: CharSequence,
        items: List<CharSequence>,
        checkedItem: Int,
        onClick: (DialogInterface, Int) -> Unit,
        onPositiveClick: (DialogInterface, Int) -> Unit,
        onNegativeClick: (DialogInterface, Int) -> Unit
) {
    requireActivity().singleChoiceSelector(title, items, checkedItem, onClick,
            onPositiveClick, onNegativeClick)
}

fun Fragment.multiChoiceSelector(
        title: CharSequence,
        items: List<CharSequence>,
        checkedItems: List<Boolean>,
        onClick: (DialogInterface, Int, Boolean) -> Unit,
        onPositiveClick: (DialogInterface, Int) -> Unit,
        onNegativeClick: (DialogInterface, Int) -> Unit
) {
    requireActivity().multiChoiceSelector(title, items, checkedItems, onClick,
            onPositiveClick, onNegativeClick)
}