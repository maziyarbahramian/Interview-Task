package com.maziyar.interview.ui.customViews

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Typeface
import android.text.*
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.*
import androidx.appcompat.widget.AppCompatEditText
import com.maziyar.interview.ui.customViews.listPopupWindwo.CustomListPopupWindow
import com.maziyar.interview.ui.customViews.listPopupWindwo.OnPopupMenuItemClickListener
import com.maziyar.interview.ui.customViews.listPopupWindwo.PopupIds


class CustomEditText : AppCompatEditText {

    private val TAG = "CustomEditText"
    var offsety = 0
    var offsetx = 0
    var anchor: View? = null

    private lateinit var selectionMenu: CustomListPopupWindow

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet)

    constructor(
        context: Context,
        attributeSet: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attributeSet, defStyleAttr)


    init {

        customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean = true
            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean = false
            override fun onDestroyActionMode(p0: ActionMode?) {}

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                showCustomSelectionMenu()
                p1?.clear()
                return false
            }
        }
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        if (this::selectionMenu.isInitialized) {
            if (selectionMenu.isShowing)
                selectionMenu.dismiss()
        }
    }

    private fun showCustomSelectionMenu() {
        if (this@CustomEditText::selectionMenu.isInitialized.not()) {
            initSelectionMenu()
        }
        if (selectionMenu.isShowing.not()) {
            if (offsetx > width / 2)
                offsetx -= 550
            else
                offsetx += 80
            selectionMenu.horizontalOffset = offsetx
            if (offsety < height / 2)
                offsety += 80
            else
                offsety -= 80
            selectionMenu.verticalOffset = offsety
            selectionMenu.show()
        }
    }

    private fun initSelectionMenu() {
        selectionMenu = CustomListPopupWindow.getEditTextSelectionMenu(
            context,
            anchor ?: this,
            OnPopupMenuItemClickListener {
                when (it.id) {
                    PopupIds.COPY -> copySelectedTextToClipboard()
                    PopupIds.PASTE -> pasteDataFromClipboard()
                    PopupIds.BOLD -> makeSelectedTextBold()
                    PopupIds.ITALIC -> makeSelectedTextItalic()
                    PopupIds.NORMAL -> makeSelectedTextNormal()
                }
                setSelection(0)
            }
        )
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_UP) {
            offsetx = event.x.toInt()
            offsety = event.y.toInt()
        }

        return super.onTouchEvent(event)
    }

    private fun pasteDataFromClipboard() {
        val clipboard: ClipboardManager =
            context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val item = clipboard.primaryClip?.getItemAt(0)
        val pasteDate = item?.text
        pasteDate?.let { data ->
            text?.replace(selectionStart, selectionEnd, data)
        }
    }

    private fun copySelectedTextToClipboard() {
        val clipboard: ClipboardManager =
            context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(
            "bazaar-interview",
            text?.subSequence(selectionStart, selectionEnd)
        )
        clipboard.setPrimaryClip(clip)
    }

    private fun makeSelectedTextBold() {
        val spannable = SpannableString(text)
        val boldSpan = StyleSpan(Typeface.BOLD)
        val flag = Spannable.SPAN_INCLUSIVE_INCLUSIVE
        spannable.setSpan(boldSpan, selectionStart, selectionEnd, flag)
            .toString()
        setText(spannable)
    }

    private fun makeSelectedTextItalic() {
        val spannable = SpannableString(text)
        val italicSpan = StyleSpan(Typeface.ITALIC)
        val flag = Spannable.SPAN_INCLUSIVE_INCLUSIVE
        spannable.setSpan(italicSpan, selectionStart, selectionEnd, flag)
            .toString()
        setText(spannable)
    }

    private fun makeSelectedTextNormal() {
        val spannable = SpannableString(text)
        val spans = spannable.getSpans(selectionStart, selectionEnd, StyleSpan::class.java)
        for (span in spans) {
            spannable.removeSpan(span)
        }
        setText(spannable)
    }
}