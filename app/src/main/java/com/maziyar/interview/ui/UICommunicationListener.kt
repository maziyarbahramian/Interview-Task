package com.maziyar.interview.ui

import android.view.View

interface UICommunicationListener {
    fun hideSoftKeyboard()

    fun setToolbarVisibility(visibility: Int)

    fun setBackButtonVisibility(visibility: Int)

    fun setMenuButtonVisibility(visibility: Int)

    fun setBackButtonClickListener(clickListener: View.OnClickListener)

    fun setMenuButtonClickListener(clickListener: View.OnClickListener)

    fun setTitle(title: String)
}