package io.github.anderscheow.library.recyclerView.adapters

import android.content.Context
import java.util.*

abstract class SortableRecyclerViewAdapter<Value>(
        context: Context
) : BaseRecyclerViewAdapter<Value>(context) {

    fun setItems(items: MutableList<Value>?, comparator: Comparator<Value>) {
        items?.let {
            setItemsWithoutNotify(it)
            sort(comparator)
        }
    }

    private fun sort(comparator: Comparator<Value>) {
        items.let {
            Collections.sort(it, comparator)

            notifyDataSetChanged()
        }
    }
}
