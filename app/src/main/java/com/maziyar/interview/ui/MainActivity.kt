package com.maziyar.interview.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.maziyar.interview.R
import com.maziyar.interview.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UICommunicationListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            inputMethodManager
                .hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    override fun setToolbarVisibility(visibility: Int) {
        binding.toolbar.root.visibility = visibility
    }

    override fun setBackButtonClickListener(clickListener: View.OnClickListener) {
        binding.toolbar.backButton.setOnClickListener(clickListener)
    }

    override fun setMenuButtonClickListener(clickListener: View.OnClickListener) {
        binding.toolbar.menuButton.setOnClickListener(clickListener)
    }

    override fun setTitle(title: String) {
        binding.toolbar.titleTextView.text = title
    }

    override fun setBackButtonVisibility(visibility: Int) {
        binding.toolbar.backButton.visibility = visibility
    }

    override fun setMenuButtonVisibility(visibility: Int) {
        binding.toolbar.menuButton.visibility = visibility
    }
}