package com.maziyar.interview.ui.customViews.listPopupWindwo

class OnPopupMenuItemClickListener(
    private val onItemClickListener: (popupItem: PopupListItem) -> Unit
) {
    fun onClick(popupItem: PopupListItem) = onItemClickListener(popupItem)
}