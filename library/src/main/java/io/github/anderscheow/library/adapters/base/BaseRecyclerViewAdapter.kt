package io.github.anderscheow.library.adapters.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import java.util.*

abstract class BaseRecyclerViewAdapter<T>(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    var items: MutableList<T> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItemsWithoutNotify(items: MutableList<T>?) {
        items?.let {
            this.items = it
        }
    }

    @Suppress("UNUSED")
    fun addItems(items: List<T>?) {
        items?.let {
            for (item in it) {
                addItem(item)
            }
        }
    }

    fun addItem(item: T?) {
        item?.let {
            items.add(item)
            notifyItemInserted(items.size - 1)
        }
    }
}
