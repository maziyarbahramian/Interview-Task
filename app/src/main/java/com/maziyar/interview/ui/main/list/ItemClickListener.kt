package com.maziyar.interview.ui.main.list

import android.view.View


class ItemClickListener<T>(
    private val onItemClickListener: (listItem: T) -> Unit,
    private val showOverflowMenu: (listItem: T, anchorView: View) -> Unit,
) {
    fun onItemClick(listItem: T) = onItemClickListener(listItem)

    fun showOverFlowMenu(listItem: T, anchorView: View) = showOverflowMenu(listItem, anchorView)
}

