package com.maziyar.interview.ui.customViews

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import com.maziyar.interview.R
import com.maziyar.interview.databinding.LayoutCustomDialogBinding

class CustomDialog(context: Context) : Dialog(context) {
    private val TAG = "CustomDialog"

    companion object {
        fun getAddFolderDialog(context: Context): CustomDialog {
            return CustomDialog(context)
                .setTitleText(R.string.add_folder_dialog_title)
                .setDescription(R.string.add_folder_dialog_description)
                .setInputHint(R.string.dialog_folder_title)
                .setAcceptButtonText(R.string.dialog_add_folder_button)
                .setCancelButtonText(R.string.dialog_cancel_button)
        }

        fun getDeleteNoteDialog(context: Context): CustomDialog {
            return CustomDialog(context)
                .setTitleText(R.string.delete_note_dialog_title)
                .setDescription(R.string.delete_note_dialog_description)
                .setAcceptButtonText(R.string.dialog_delete_button)
                .setCancelButtonText(R.string.dialog_cancel_button)
        }

        fun getDeleteFolderDialog(context: Context): CustomDialog {
            return CustomDialog(context)
                .setTitleText(R.string.delete_folder_dialog_title)
                .setDescription(R.string.delete_folder_dialog_description)
                .setAcceptButtonText(R.string.dialog_delete_button)
                .setCancelButtonText(R.string.dialog_cancel_button)
        }

        fun getRenameFolderDialog(context: Context): CustomDialog {
            return CustomDialog(context)
                .setTitleText(R.string.rename_folder_dialog_title)
                .setInputHint(R.string.dialog_folder_title)
                .setAcceptButtonText(R.string.dialog_save_button)
                .setCancelButtonText(R.string.dialog_cancel_button)
        }
    }

    private var binding: LayoutCustomDialogBinding


    init {
        window?.let { window ->
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.attributes.windowAnimations = R.style.DialogAnimation
        }

        binding = LayoutCustomDialogBinding.inflate(LayoutInflater.from(context))

        setContentView(binding.root)
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun makeInputVisible(): CustomDialog {
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
        binding.title.text = string
        return this
    }

    fun setTitleText(@StringRes resId: Int): CustomDialog {
        binding.title.text = context.getString(resId)
        return this
    }

    fun setDescription(string: String): CustomDialog {
        binding.description.text = string
        return this
    }

    fun setDescription(@StringRes resId: Int): CustomDialog {
        binding.description.visibility = View.VISIBLE
        binding.description.text = context.getString(resId)
        return this
    }


    fun setAcceptButtonText(string: String): CustomDialog {
        binding.acceptButton.text = string
        return this
    }

    fun setAcceptButtonText(@StringRes resId: Int): CustomDialog {
        binding.acceptButton.text = context.getString(resId)
        return this
    }

    fun setCancelButtonText(string: String): CustomDialog {
        binding.cancelButton.text = string
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

    fun setInputText(string: String): CustomDialog {
        if (binding.input.visibility == View.GONE || binding.input.visibility == View.INVISIBLE) {
            throw IllegalStateException("!!! Input EditText isn't visible, set it with makeInputVisible()!!!")
        }
        binding.input.setText(string)
        return this
    }

}