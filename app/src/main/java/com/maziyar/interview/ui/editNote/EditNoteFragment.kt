package com.maziyar.interview.ui.editNote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maziyar.interview.R
import com.maziyar.interview.databinding.FragmentEditNoteBinding
import com.maziyar.interview.persistence.entities.Note
import com.maziyar.interview.utils.extensions.toPersianDigit
import dagger.hilt.android.AndroidEntryPoint
import saman.zamani.persiandate.PersianDate
import java.util.*

@AndroidEntryPoint
class EditNoteFragment : Fragment() {
    private val TAG = "EditNoteFragment"

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditNoteViewModel by viewModels()

    private val args: EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        setupToolbar()
        initDateTextView()
        getExistingNote()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getExistingNote() {
        if (args.noteId != -1L) {
            viewModel.getExistingNote(args.noteId)

            viewModel.existingNoteLiveData.observe(viewLifecycleOwner, {
                binding.noteTitleEditText.setText(it.title)
                binding.noteBodyEditText.setText(it.body)
                initDateTextView(PersianDate(it.date))
                setViewModelNote(it)
            })
        }

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

    private fun initDateTextView(persianDate: PersianDate = PersianDate()) {
        binding.dateTextView.text = getString(R.string.date_text_format).format(
            persianDate.shDay.toString().toPersianDigit(),
            persianDate.monthName(persianDate.shMonth),
            persianDate.shYear.toString().toPersianDigit()
        )
    }

    private fun setViewModelNote(note: Note = Note()) {
        val title = binding.noteTitleEditText.text.toString()
        val body = binding.noteBodyEditText.text.toString()

        note.title = if (title.isNotEmpty()) title else getString(R.string.note_default_title)
        note.body = body
        note.folder_id = args.folderId

        if (note.date == null)
            note.date = Date()
        viewModel.setNote(note)
    }


    override fun onDestroyView() {
        setViewModelNote()
        viewModel.insertNote()
        super.onDestroyView()
    }


}