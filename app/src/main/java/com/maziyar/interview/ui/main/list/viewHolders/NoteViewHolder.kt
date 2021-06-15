package com.maziyar.interview.ui.main.list.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maziyar.interview.R
import com.maziyar.interview.databinding.ListItemNoteBinding
import com.maziyar.interview.persistence.views.ListItem
import com.maziyar.interview.persistence.entities.Note
import com.maziyar.interview.ui.main.list.ItemClickListener
import com.maziyar.interview.utils.extensions.toPersianDigit
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
        itemClickListener: ItemClickListener<ListItem>
    ) {
        view.apply {
            titleTextView.text = item.title
            subTitleTextView.text = getSubTitleText(item.date)

            root.setOnClickListener { itemClickListener.onItemClick(item) }
            optionsButton.setOnClickListener {
                itemClickListener.showOverFlowMenu(
                    item,
                    view.optionsButton
                )
            }
        }
    }

    fun bind(
        item: Note,
        itemClickListener: ItemClickListener<Note>
    ) {
        view.apply {
            titleTextView.text = item.title
            subTitleTextView.text = getSubTitleText(item.date!!)

            root.setOnClickListener { itemClickListener.onItemClick(item) }
            optionsButton.setOnClickListener {
                itemClickListener.showOverFlowMenu(
                    item,
                    view.optionsButton
                )
            }
        }
    }

    private fun getSubTitleText(date: Date): String {
        val diff = Date().time - date.time
        val days = TimeUnit.MILLISECONDS.toDays(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)

        val context = view.root.context
        return when {
            days > 0 -> {
                context.getString(R.string.note_view_holder_subtitle_day_format)
                    .format(days.toString().toPersianDigit())
            }
            hours > 0 -> {
                context.getString(R.string.note_view_holder_subtitle_hour_format)
                    .format(hours.toString().toPersianDigit())
            }
            minutes > 0 -> {
                context.getString(R.string.note_view_holder_subtitle_minute_format)
                    .format(minutes.toString().toPersianDigit())
            }
            seconds > 0 -> {
                context.getString(R.string.note_view_holder_subtitle_second_format)
                    .format(seconds.toString().toPersianDigit())
            }
            else -> "چند لحظه پیش"
        }

    }

}