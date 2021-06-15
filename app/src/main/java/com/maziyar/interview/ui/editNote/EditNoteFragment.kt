package com.maziyar.interview.ui.editNote

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpanned
import androidx.fragment.app.Fragment
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.noteBodyEditText.anchor = binding.anchorView
    }

    private fun getExistingNote() {
        if (args.noteId != -1L) {
            viewModel.getExistingNote(args.noteId)

            viewModel.existingNoteLiveData.observe(viewLifecycleOwner, {
                binding.noteTitleEditText.setText(it.title)
                binding.noteBodyEditText.setText(
                    HtmlCompat.fromHtml(
                        it.body,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )
                )
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

            if (args.noteId != -1L)
                menuButton.visibility = View.VISIBLE
            else menuButton.visibility = View.GONE

            menuButton.setOnClickListener {
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
            deleteNote(noteId)
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
        val body = binding.noteBodyEditText.text

        note.title = title
        note.body =
            HtmlCompat.toHtml(
                body!!.toSpanned(),
                HtmlCompat.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE
            )
        note.folder_id = args.folderId

        if (note.date == null)
            note.date = Date()
        viewModel.setNote(note)
    }

    private fun deleteNote(noteId: Long) {
        viewModel.deleteNote(noteId)
        binding.noteBodyEditText.setText("")
        binding.noteTitleEditText.setText("")
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