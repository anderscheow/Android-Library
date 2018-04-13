package io.github.anderscheow.library.adapters.base

import android.content.Context
import java.util.*

@Suppress("UNUSED")
abstract class SortableRecyclerViewAdapter<T>(context: Context) : BaseRecyclerViewAdapter<T>(context) {

    @Suppress("UNUSED")
    fun setItems(items: MutableList<T>?, comparator: Comparator<T>) {
        items?.let {
            setItemsWithoutNotify(it)
            sort(comparator)
        }
    }

    private fun sort(comparator: Comparator<T>) {
        items.let {
            Collections.sort(it, comparator)

            notifyDataSetChanged()
        }
    }
}
