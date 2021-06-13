package com.maziyar.interview.ui.main.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.maziyar.interview.persistence.views.ListItem
import com.maziyar.interview.ui.main.list.viewHolders.FolderViewHolder
import com.maziyar.interview.ui.main.list.viewHolders.NoteViewHolder
import java.lang.ClassCastException

class MainAdapter(
    val folderItemClickListener: ItemClickListener<ListItem>,
    val noteItemClickListener: ItemClickListener<ListItem>,
) : ListAdapter<ListItem, RecyclerView.ViewHolder>(DiffCallBack()) {

    private val ITEM_VIEW_TYPE_NOTE = 0
    private val ITEM_VIEW_TYPE_FOLDER = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_NOTE -> NoteViewHolder.from(parent)
            ITEM_VIEW_TYPE_FOLDER -> FolderViewHolder.from(parent)
            else -> throw ClassCastException("Unknown View Type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NoteViewHolder -> {
                holder.bind(getItem(position), noteItemClickListener)
            }
            is FolderViewHolder -> {
                holder.bind(getItem(position), folderItemClickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }
}

class DiffCallBack : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.id == newItem.id && oldItem.type == newItem.type
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }

}