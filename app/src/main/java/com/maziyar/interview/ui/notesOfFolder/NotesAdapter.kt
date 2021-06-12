package com.maziyar.interview.ui.notesOfFolder

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.maziyar.interview.persistence.entities.Note
import com.maziyar.interview.ui.main.list.ItemClickListener
import com.maziyar.interview.ui.main.list.viewHolders.NoteViewHolder

class NotesAdapter(
    val noteItemClickListener: ItemClickListener<Note>
) : ListAdapter<Note, NoteViewHolder>(NotesDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position), noteItemClickListener)
    }
}


class NotesDiffCallBack : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}