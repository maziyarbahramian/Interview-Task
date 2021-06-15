package com.maziyar.interview.ui.main.list.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maziyar.interview.R
import com.maziyar.interview.databinding.ListItemFolderBinding
import com.maziyar.interview.persistence.views.ListItem
import com.maziyar.interview.ui.main.list.ItemClickListener
import com.maziyar.interview.utils.extensions.toPersianDigit

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

    fun bind(
        item: ListItem,
        itemClickListener: ItemClickListener<ListItem>
    ) {
        view.apply {
            titleTextView.text = item.title

            subTitleTextView.text =
                root.context.getString(R.string.folder_view_holder_subtitle_format)
                    .format(item.subTitle.toPersianDigit())

            root.setOnClickListener { itemClickListener.onItemClick(item) }

            optionsButton.setOnClickListener {
                itemClickListener.showOverFlowMenu(
                    item,
                    view.optionsButton
                )
            }
        }
    }
}