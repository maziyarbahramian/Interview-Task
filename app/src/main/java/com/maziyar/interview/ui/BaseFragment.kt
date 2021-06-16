package com.maziyar.interview.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {

    private val TAG = "BaseFragment"

    lateinit var uiCommunicationListener: UICommunicationListener

    protected abstract val pageTitle: String

    protected abstract val toolbarVisibility: Int
    protected abstract val backButtonVisibility: Int
    protected abstract val menuButtonVisibility: Int

    protected abstract var menuButtonClickListener: View.OnClickListener?


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiCommunicationListener.setTitle(pageTitle)
        uiCommunicationListener.setToolbarVisibility(toolbarVisibility)
        uiCommunicationListener.setBackButtonVisibility(backButtonVisibility)
        uiCommunicationListener.setMenuButtonVisibility(menuButtonVisibility)

        menuButtonClickListener?.let {
            uiCommunicationListener.setMenuButtonClickListener(it)
        }

        uiCommunicationListener.setBackButtonClickListener {
            popBackStack()
        }
    }

    protected fun popBackStack() {
        findNavController().popBackStack()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            uiCommunicationListener = context as UICommunicationListener
        } catch (e: ClassCastException) {
            Log.e(TAG, "$context must implement UICommunicationListener")
        }
    }
}