package com.maziyar.interview.ui.customViews.listPopupWindwo

import androidx.annotation.DrawableRes

data class PopupListItem(
    val id: PopupIds,
    val title: String,
    @DrawableRes val icon: Int
)
