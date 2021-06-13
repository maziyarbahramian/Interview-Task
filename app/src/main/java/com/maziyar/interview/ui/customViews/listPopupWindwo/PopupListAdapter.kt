package com.maziyar.interview.ui.customViews.listPopupWindwo

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.maziyar.interview.R
import com.maziyar.interview.databinding.ListItemPopupMenuBinding


class PopupListAdapter(
    context: Context,
    list: ArrayList<PopupListItem>,
    private val popupMenuItemClick: OnPopupMenuItemClickListener,
    val dismiss: () -> Unit
) :
    ArrayAdapter<PopupListItem>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var binding: ListItemPopupMenuBinding? = null
        if (binding == null) {
            binding = ListItemPopupMenuBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        }

        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        binding.root.setBackgroundResource(R.drawable.popup_list_item_ripple)

        val currentOption = getItem(position)

        binding.root.setOnClickListener {
            popupMenuItemClick.onClick(currentOption!!)
            dismiss()
        }

        binding.popupImageView.setImageResource(currentOption?.icon!!)
        binding.popupTextView.text = currentOption.title

        return binding.root
    }

}