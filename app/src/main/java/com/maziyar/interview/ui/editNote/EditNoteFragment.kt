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
import com.maziyar.interview.ui.customViews.CustomDialog
import com.maziyar.interview.ui.customViews.listPopupWindwo.CustomListPopupWindow
import com.maziyar.interview.ui.customViews.listPopupWindwo.OnPopupMenuItemClickListener
import com.maziyar.interview.ui.customViews.listPopupWindwo.PopupIds
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

            menuButton.setOnClickListener {
                if (args.noteId != -1L)
                    showOverFloeMenu(args.noteId, it)
            }
        }
    }

    private fun showOverFloeMenu(noteId: Long, anchorView: View) {
        CustomListPopupWindow.getDeletePopupWindow(
            context = requireContext(),
            anchor = anchorView,
            popupMenuItemClick = OnPopupMenuItemClickListener { popupItem ->
                if (popupItem.id == PopupIds.DELETE) {
                    showDeleteNoteDialog(noteId)
                }
            }
        ).show()
    }

    private fun showDeleteNoteDialog(noteId: Long) {
        val dialog = CustomDialog.getDeleteNoteDialog(requireContext())

        dialog.setAcceptButtonClickListener {
            viewModel.deleteNote(noteId)
            binding.noteBodyEditText.setText("")
            binding.noteTitleEditText.setText("")
            dialog.dismiss()
            backToPreviousPage()
        }
        dialog.show()
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

        note.title = title
        note.body = body
        note.folder_id = args.folderId

        if (note.date == null)
            note.date = Date()
        viewModel.setNote(note)
    }

    private fun backToPreviousPage() {
        findNavController().popBackStack()
    }


    override fun onDestroyView() {
        setViewModelNote()
        viewModel.insertNote()
        super.onDestroyView()
    }


}