package com.maziyar.interview.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maziyar.interview.databinding.ListItemFolderBinding
import com.maziyar.interview.persistence.entities.ListItem

class FolderViewHolder(
    private val view: ListItemFolderBinding
) : RecyclerView.ViewHolder(view.root) {

    companion object {
        fun from(parent: ViewGroup): FolderViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = ListItemFolderBinding.inflate(layoutInflater, parent, false)
            return FolderViewHolder(view)
        }
    }

    fun bind(item: ListItem) {
        view.apply {
            titleTextView.text = item.title
            subTitleTextView.text = item.subTitle
        }
    }
}