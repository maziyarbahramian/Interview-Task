package com.maziyar.interview.ui.addNote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maziyar.interview.R
import com.maziyar.interview.databinding.FragmentEditNoteBinding
import com.maziyar.interview.persistence.entities.Note
import com.maziyar.interview.utils.extensions.toPersianDigit
import dagger.hilt.android.AndroidEntryPoint
import saman.zamani.persiandate.PersianDate
import java.util.*

@AndroidEntryPoint
class EditNoteFragment : Fragment() {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditNoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        initDateTextView()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            backButton.visibility = View.VISIBLE
            menuButton.visibility = View.VISIBLE

            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun initDateTextView() {
        val persianDate = PersianDate()
        binding.dateTextView.text = getString(R.string.date_text_format).format(
            persianDate.shDay.toString().toPersianDigit(),
            persianDate.monthName(persianDate.shMonth),
            persianDate.shYear.toString().toPersianDigit()
        )
    }

    private fun setViewModelNote() {
        val title = binding.noteTitleEditText.text.toString()
        val body = binding.noteBodyEditText.text.toString()
        val note = Note(
            title = if (title.isNotEmpty()) title else getString(R.string.note_default_title),
            body = body,
            id = null,
            date = Date()
        )
        viewModel.setNote(note)
    }


    override fun onDestroyView() {
        setViewModelNote()
        viewModel.insertNote()
        super.onDestroyView()
    }


}