package io.github.anderscheow.library.recyclerView.util

import androidx.recyclerview.widget.DiffUtil

class SectionGroup<S, R> {

    var section: S? = null
        private set

    var row: R? = null
        private set

    var isRow: Boolean = false
        private set

    var requiredFooter: Boolean = false
        private set

    override fun equals(other: Any?): Boolean {
        (other as? SectionGroup<*, *>)?.let {
            return section == other.section &&
                    row == other.row &&
                    isRow == other.isRow &&
                    requiredFooter == other.requiredFooter
        }
        return false
    }

    override fun hashCode(): Int {
        return section.hashCode() + row.hashCode() + isRow.hashCode() + requiredFooter.hashCode()
    }

    companion object {

        fun<S, R> getDiffCallback(): DiffUtil.ItemCallback<SectionGroup<S, R>> {
            return object : DiffUtil.ItemCallback<SectionGroup<S, R>>() {
                override fun areItemsTheSame(oldItem: SectionGroup<S, R>, newItem: SectionGroup<S, R>): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(oldItem: SectionGroup<S, R>, newItem: SectionGroup<S, R>): Boolean {
                    return oldItem == newItem
                }
            }
        }

        fun<S, R> createSection(section: S): SectionGroup<S, R> {
            return SectionGroup<S, R>().apply {
                this.section = section
                this.isRow = false
                this.requiredFooter = false
            }
        }

        fun<S, R> createRow(row: R): SectionGroup<S, R> {
            return SectionGroup<S, R>().apply {
                this.row = row
                this.isRow = true
                this.requiredFooter = false
            }
        }

        fun<S, R> createFooter(): SectionGroup<S, R> {
            return SectionGroup<S, R>().apply {
                this.isRow = true
                this.requiredFooter = true
            }
        }
    }
}