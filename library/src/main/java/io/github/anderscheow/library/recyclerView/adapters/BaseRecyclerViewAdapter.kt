package io.github.anderscheow.library.recyclerView.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.github.anderscheow.library.recyclerView.viewHolder.BaseViewHolder
import java.util.*

abstract class BaseRecyclerViewAdapter<Value>(
        val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: MutableList<Value> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    @LayoutRes
    protected abstract fun getBodyLayout(position: Int): Int

    protected abstract fun getBodyViewHolder(viewType: Int, binding: ViewDataBinding): RecyclerView.ViewHolder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                layoutInflater, viewType, parent, false)

        return getBodyViewHolder(viewType, binding)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)

        if (itemViewType == getBodyLayout(position)) {
            if (holder !is BaseViewHolder<*>) {
                throw IllegalStateException("Must inherit BaseViewHolder for body view holder")
            }
            (holder as? BaseViewHolder<Value>)?.bind(items[holder.bindingAdapterPosition])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getBodyLayout(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItemsWithoutNotify(items: MutableList<Value>?) {
        items?.let {
            this.items = it
        }
    }

    fun addItems(items: List<Value>?) {
        items?.let {
            for (item in it) {
                addItem(item)
            }
        }
    }

    fun addItem(item: Value?) {
        item?.let {
            items.add(item)
            notifyItemInserted(items.size - 1)
        }
    }
}
