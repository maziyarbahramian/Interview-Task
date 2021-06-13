package com.maziyar.interview.ui.customViews.listPopupWindwo

import android.R.menu
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.View
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import com.google.android.material.elevation.ElevationOverlayProvider
import com.google.android.material.shape.MaterialShapeDrawable
import com.maziyar.interview.R


class CustomListPopupWindow(
    context: Context,
    anchor: View,
    items: ArrayList<PopupListItem>,
    popupMenuItemClick: OnPopupMenuItemClickListener
) : ListPopupWindow(context) {

    init {
        val metrics = context.resources.displayMetrics
        height = WRAP_CONTENT
        width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160f, metrics).toInt()
        isModal = true
        anchorView = anchor
        horizontalOffset = 20
        verticalOffset = 20


        setAdapter(
            PopupListAdapter(
                context, items, popupMenuItemClick
            ) {
                dismiss()
            }
        )


        setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.popup_window_shape))
    }

    companion object {

        private val renameItem =
            PopupListItem(PopupIds.RENAME, "تغییر نام", R.drawable.ic_edit)


        private val deleteItem =
            PopupListItem(PopupIds.DELETE, "حذف", R.drawable.ic_delete)

        private val renameAndDeleteList = arrayListOf(
            renameItem,
            deleteItem
        )


        fun getRenameAndDeletePopupWindow(
            context: Context,
            anchor: View,
            popupMenuItemClick: OnPopupMenuItemClickListener
        ): CustomListPopupWindow {
            return CustomListPopupWindow(
                context,
                anchor,
                renameAndDeleteList,
                popupMenuItemClick
            )
        }

        fun getRenamePopupWindow(
            context: Context,
            anchor: View,
            popupMenuItemClick: OnPopupMenuItemClickListener
        ): CustomListPopupWindow {
            return CustomListPopupWindow(
                context,
                anchor,
                arrayListOf(renameItem),
                popupMenuItemClick
            )
        }

        fun getDeletePopupWindow(
            context: Context,
            anchor: View,
            popupMenuItemClick: OnPopupMenuItemClickListener
        ): CustomListPopupWindow {
            return CustomListPopupWindow(
                context,
                anchor,
                arrayListOf(deleteItem),
                popupMenuItemClick
            )
        }

    }
}

enum class PopupIds {
    RENAME, DELETE
}