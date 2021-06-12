package com.maziyar.interview.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maziyar.interview.databinding.ListItemNoteBinding
import com.maziyar.interview.persistence.entities.ListItem

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

    fun bind(item: ListItem) {
        view.apply {
            titleTextView.text = item.title
            subTitleTextView.text = item.subTitle
        }
    }

}