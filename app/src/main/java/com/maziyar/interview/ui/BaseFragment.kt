package com.maziyar.interview.ui

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    private val TAG = "BaseFragment"

    lateinit var uiCommunicationListener: UICommunicationListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement UICommunicationListener")
        }
    }
}