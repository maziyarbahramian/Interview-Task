package com.maziyar.interview.ui.editNote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpanned
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maziyar.interview.R
import com.maziyar.interview.databinding.FragmentEditNoteBinding
import com.maziyar.interview.persistence.entities.Note
import com.maziyar.interview.ui.BaseFragment
import com.maziyar.interview.ui.customViews.CustomDialog
import com.maziyar.interview.ui.customViews.listPopupWindwo.CustomListPopupWindow
import com.maziyar.interview.ui.customViews.listPopupWindwo.OnPopupMenuItemClickListener
import com.maziyar.interview.ui.customViews.listPopupWindwo.PopupIds
import com.maziyar.interview.utils.extensions.toPersianDigit
import dagger.hilt.android.AndroidEntryPoint
import saman.zamani.persiandate.PersianDate
import java.util.*


@AndroidEntryPoint
class EditNoteFragment : BaseFragment() {
    private val TAG = "EditNoteFragment"

    override val pageTitle: String = ""

    override val toolbarVisibility: Int
        get() = View.VISIBLE
    override val backButtonVisibility: Int
        get() = View.VISIBLE
    override val menuButtonVisibility: Int
        get() {
            return if (args.noteId != -1L) View.VISIBLE else View.GONE
        }

    override var menuButtonClickListener: View.OnClickListener? =
        View.OnClickListener(::showOverFloeMenu)


    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EditNoteViewModel by viewModels()

    private val args: EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDateTextView()
        getExistingNote()
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


    private fun showOverFloeMenu(anchorView: View) {
        CustomListPopupWindow.getDeletePopupWindow(
            context = requireContext(),
            anchor = anchorView,
            popupMenuItemClick = OnPopupMenuItemClickListener { popupItem ->
                if (popupItem.id == PopupIds.DELETE) {
                    showDeleteNoteDialog(args.noteId)
                }
            }
        ).show()
    }

    private fun showDeleteNoteDialog(noteId: Long) {
        val dialog = CustomDialog.getDeleteNoteDialog(requireContext())

        dialog.setAcceptButtonClickListener {
            deleteNote(noteId)
            dialog.dismiss()
            popBackStack()
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


    override fun onDestroyView() {
        setViewModelNote()
        viewModel.insertNote()
        uiCommunicationListener.hideSoftKeyboard()
        super.onDestroyView()
    }


}