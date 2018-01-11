package io.github.anderscheow.library.adapters.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater

import java.util.ArrayList

abstract class BaseRecyclerViewAdapter<T>(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private var items: MutableList<T> = ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItems(): MutableList<T> {
        return items
    }

    fun setItems(items: MutableList<T>) {
        this.items = items

        notifyDataSetChanged()
    }

    fun setItemsWithoutNotify(items: MutableList<T>?) {
        items?.let {
            this.items = it
        }
    }

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
