package com.maziyar.interview.ui.customViews.listPopupWindwo

import android.R.menu
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import com.google.android.material.elevation.ElevationOverlayProvider
import com.google.android.material.shape.MaterialShapeDrawable
import com.maziyar.interview.R


class CustomListPopupWindow(
    context: Context,
    anchor: View,
    items: ArrayList<PopupListItem>,
    popupMenuItemClick: OnPopupMenuItemClickListener,
    isModal: Boolean = true,
    horizontalOffset: Int = 20,
    verticalOffset: Int = 20
) : ListPopupWindow(context) {

    init {
        val metrics = context.resources.displayMetrics
        height = WRAP_CONTENT
        width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160f, metrics).toInt()
        this.isModal = isModal
        anchorView = anchor
        this.horizontalOffset = horizontalOffset
        this.verticalOffset = verticalOffset


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

        private val editTextMenuItemList = arrayListOf(
            PopupListItem(PopupIds.COPY, "کپی", R.drawable.ic_copy),
            PopupListItem(PopupIds.PASTE, "پیست", R.drawable.ic_paste),
            PopupListItem(PopupIds.BOLD, "بولد", R.drawable.ic_bold),
            PopupListItem(PopupIds.ITALIC, "ایتالیک", R.drawable.ic_italic),
            PopupListItem(PopupIds.NORMAL, "ساده", R.drawable.ic_normal_text)
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

        fun getEditTextSelectionMenu(
            context: Context,
            anchor: View,
            popupMenuItemClick: OnPopupMenuItemClickListener
        ): CustomListPopupWindow {
            return CustomListPopupWindow(
                context,
                anchor,
                editTextMenuItemList,
                popupMenuItemClick,
                false
            )
        }

    }

}

class PopupIds {
    companion object {
        const val RENAME = 1
        const val DELETE = 2
        const val COPY = 3
        const val PASTE = 4
        const val BOLD = 5
        const val ITALIC = 6
        const val NORMAL = 7
    }
}

