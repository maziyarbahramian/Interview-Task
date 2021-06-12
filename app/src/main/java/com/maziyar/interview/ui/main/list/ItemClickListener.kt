package com.maziyar.interview.ui.main.list

class ItemClickListener(
    private val onItemClickListener: (itemId: Long) -> Unit,
    private val showOverflowMenu: (itemId: Long) -> Unit,
) {
    fun onItemClick(itemId: Long) = onItemClickListener(itemId)

    fun showOverFlowMenu(itemId: Long) = showOverflowMenu(itemId)
}

