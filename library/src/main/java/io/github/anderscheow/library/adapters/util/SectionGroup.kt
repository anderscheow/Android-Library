package io.github.anderscheow.library.adapters.util

import androidx.recyclerview.widget.DiffUtil

class SectionGroup {

    var section: Any? = null
        private set

    var row: Any? = null
        private set

    var isRow: Boolean = false
        private set

    var requiredFooter: Boolean = false
        private set

    companion object {

        var DIFF_CALLBACK: DiffUtil.ItemCallback<SectionGroup> = object : DiffUtil.ItemCallback<SectionGroup>() {
            override fun areItemsTheSame(oldItem: SectionGroup, newItem: SectionGroup): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: SectionGroup, newItem: SectionGroup): Boolean {
                return false
            }
        }

        fun createSection(section: Any): SectionGroup {
            return SectionGroup().apply {
                this.section = section
                this.isRow = false
                this.requiredFooter = false
            }
        }

        fun createRow(row: Any): SectionGroup {
            return SectionGroup().apply {
                this.row = row
                this.isRow = true
                this.requiredFooter = false
            }
        }

        fun createFooter(): SectionGroup {
            return SectionGroup().apply {
                this.isRow = true
                this.requiredFooter = true
            }
        }
    }
}