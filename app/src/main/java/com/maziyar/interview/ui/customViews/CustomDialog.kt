package com.maziyar.interview.ui.customViews

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.annotation.StringRes
import com.maziyar.interview.R
import com.maziyar.interview.databinding.LayoutCustomDialogBinding

class CustomDialog(context: Activity) : Dialog(context) {
    private val TAG = "CustomDialog"
    private var binding: LayoutCustomDialogBinding


    init {
        window?.let { window ->
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.attributes.windowAnimations = R.style.DialogAnimation
        }

        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display = context.display
            display?.getRealMetrics(displayMetrics)
        } else {
            @Suppress("DEPRECATION")
            val display = context.windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(displayMetrics)
        }

        val displayWidth = displayMetrics.widthPixels
        val displayHeight = displayMetrics.heightPixels

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window!!.attributes)

        val dialogWindowWidth = (displayWidth * 0.9).toInt()

        binding = LayoutCustomDialogBinding.inflate(LayoutInflater.from(context))
        binding.root.minimumWidth = dialogWindowWidth
        setContentView(binding.root)
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    fun makeInputVisible(): CustomDialog {
        binding.input.visibility = View.VISIBLE
        return this
    }

    fun setAcceptButtonClickListener(listener: View.OnClickListener): CustomDialog {
        binding.acceptButton.setOnClickListener {
            listener.onClick(it)
        }
        return this
    }

    fun setCancelButtonClickListener(listener: View.OnClickListener): CustomDialog {
        binding.cancelButton.setOnClickListener {
            listener.onClick(it)
            dismiss()
        }
        return this
    }

    fun setTitleText(string: String): CustomDialog {
        binding.title.text = string.toString()
        return this
    }

    fun setTitleText(@StringRes resId: Int): CustomDialog {
        binding.title.text = context.getString(resId)
        return this
    }

    fun setDescription(string: String): CustomDialog {
        binding.description.text = string.toString()
        return this
    }

    fun setDescription(@StringRes resId: Int): CustomDialog {
        binding.description.text = context.getString(resId)
        return this
    }


    fun setAcceptButtonText(string: String): CustomDialog {
        binding.acceptButton.text = string.toString()
        return this
    }

    fun setAcceptButtonText(@StringRes resId: Int): CustomDialog {
        binding.acceptButton.text = context.getString(resId)
        return this
    }

    fun setCancelButtonText(string: String): CustomDialog {
        binding.cancelButton.text = string.toString()
        return this
    }

    fun setCancelButtonText(@StringRes resId: Int): CustomDialog {
        binding.cancelButton.text = context.getString(resId)
        return this
    }

    fun setInputHint(string: String): CustomDialog {
        makeInputVisible()
        binding.input.hint = string
        return this
    }

    fun setInputHint(@StringRes resId: Int): CustomDialog {
        makeInputVisible()
        binding.input.hint = context.getString(resId)
        return this
    }

    fun getInputText(): String {
        if (binding.input.visibility == View.GONE || binding.input.visibility == View.INVISIBLE) {
            throw IllegalStateException("!!! Input EditText isn't visible, set it with makeInputVisible()!!!")
        }
        return binding.input.text.toString()
    }

}