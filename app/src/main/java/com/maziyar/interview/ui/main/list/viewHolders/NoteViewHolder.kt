package com.maziyar.interview.ui.main.list.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maziyar.interview.databinding.ListItemNoteBinding
import com.maziyar.interview.persistence.entities.ListItem
import com.maziyar.interview.ui.main.list.ItemClickListener
import java.util.*
import java.util.concurrent.TimeUnit

class NoteViewHolder(
    private val view: ListItemNoteBinding
) : RecyclerView.ViewHolder(view.root) {

    companion object {
        fun from(parent: ViewGroup): NoteViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = ListItemNoteBinding.inflate(layoutInflater, parent, false)
            return NoteViewHolder(view)
        }
    }

    fun bind(
        item: ListItem,
        itemClickListener: ItemClickListener
    ) {
        view.apply {
            titleTextView.text = item.title
            subTitleTextView.text = getSubTitleText(item.date)

            root.setOnClickListener { itemClickListener.onItemClick(item.id) }
            optionsButton.setOnClickListener { itemClickListener.showOverFlowMenu(item.id) }
        }
    }

    private fun getSubTitleText(date: Date): String {
        val diff = Date().time - date.time
        val days = TimeUnit.MILLISECONDS.toDays(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)

        return when {
            days > 0 -> {
                "%d روز پیش".format(days)
            }
            hours > 0 -> {
                "%d ساعت پیش".format(hours)
            }
            minutes > 0 -> {
                "%d دقیقه پیش".format(minutes)
            }
            seconds > 0 -> {
                "%d ثانیه پیش".format(minutes)
            }
            else -> ""
        }

    }

}