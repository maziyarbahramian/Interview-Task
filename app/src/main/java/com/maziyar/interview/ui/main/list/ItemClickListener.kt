package com.maziyar.interview.ui.main.list


class ItemClickListener<T>(
    private val onItemClickListener: (listItem: T) -> Unit,
    private val showOverflowMenu: (listItem: T) -> Unit,
) {
    fun onItemClick(listItem: T) = onItemClickListener(listItem)

    fun showOverFlowMenu(listItem: T) = showOverflowMenu(listItem)
}

