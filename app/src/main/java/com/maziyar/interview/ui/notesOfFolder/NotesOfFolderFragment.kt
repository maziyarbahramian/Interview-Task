package com.maziyar.interview.ui.notesOfFolder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.maziyar.interview.R
import com.maziyar.interview.databinding.FragmentNotesOfFolderBinding
import com.maziyar.interview.persistence.entities.Note
import com.maziyar.interview.ui.BaseFragment
import com.maziyar.interview.ui.customViews.CustomDialog
import com.maziyar.interview.ui.customViews.listPopupWindwo.CustomListPopupWindow
import com.maziyar.interview.ui.customViews.listPopupWindwo.OnPopupMenuItemClickListener
import com.maziyar.interview.ui.customViews.listPopupWindwo.PopupIds
import com.maziyar.interview.ui.main.list.ItemClickListener
import com.maziyar.interview.utils.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesOfFolderFragment : BaseFragment() {
    private val TAG = "NotesOfFolderFragment"

    override var pageTitle: String = ""

    override val toolbarVisibility: Int
        get() = View.VISIBLE

    override val backButtonVisibility: Int
        get() = View.VISIBLE

    override val menuButtonVisibility: Int
        get() = View.VISIBLE

    override var menuButtonClickListener: View.OnClickListener? =
        View.OnClickListener(::showOverflowMenuForFolder)

    private var _binding: FragmentNotesOfFolderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesOfFolderViewModel by viewModels()

    private val args: NotesOfFolderFragmentArgs by navArgs()

    private lateinit var recyclerAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesOfFolderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFabAddNote()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getFolder(args.folderId)
        viewModel.getNotesOfFolder(args.folderId)

        viewModel.folderLiveData.observe(viewLifecycleOwner, {
            pageTitle = it.name
            uiCommunicationListener.setTitle(it.name)
        })

        viewModel.notesOfFolderLiveData.observe(viewLifecycleOwner, {
            recyclerAdapter.submitList(it)
        })
    }

    private fun setupRecyclerView() {
        recyclerAdapter = NotesAdapter(
            noteItemClickListener = ItemClickListener(
                onItemClickListener = { note ->
                    moveToEditNoteFragment(note.folder_id, note.id!!)
                },
                showOverflowMenu = ::showOverflowMenuForNote
            )
        )
        binding.notesRecyclerView.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(context, 8.0f))
        }
    }

    private fun showOverflowMenuForFolder(anchorView: View) {
        CustomListPopupWindow.getRenameAndDeletePopupWindow(
            context = requireContext(),
            anchor = anchorView,
            popupMenuItemClick = OnPopupMenuItemClickListener { popupItem ->
                when (popupItem.id) {
                    PopupIds.RENAME -> {
                        showRenameFolderDialog(args.folderId)
                    }
                    PopupIds.DELETE -> {
                        showDeleteFolderDialog(args.folderId)
                    }
                }
            }
        ).show()
    }

    private fun showOverflowMenuForNote(noteItem: Note, anchorView: View) {
        CustomListPopupWindow.getDeletePopupWindow(
            context = requireContext(),
            anchor = anchorView,
            popupMenuItemClick = OnPopupMenuItemClickListener { popupItem ->
                if (popupItem.id == PopupIds.DELETE) {
                    showDeleteNoteDialog(noteItem.id!!)
                }
            }
        ).show()
    }

    private fun showDeleteNoteDialog(noteId: Long) {
        val dialog = CustomDialog.getDeleteNoteDialog(requireContext())

        dialog.setAcceptButtonClickListener {
            viewModel.deleteNote(noteId)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showRenameFolderDialog(folderId: Long) {
        val dialog = CustomDialog.getRenameFolderDialog(requireContext())
            .setInputText(pageTitle)
        dialog.setAcceptButtonClickListener {
            val newName = dialog.getInputText()
            if (newName.isEmpty()) {
                Toast.makeText(
                    context,
                    getString(R.string.please_enter_folder_name),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.renameFolder(newName, folderId)
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun showDeleteFolderDialog(folderId: Long) {
        val dialog = CustomDialog.getDeleteFolderDialog(requireContext())
        dialog.setAcceptButtonClickListener {
            viewModel.deleteFolder(folderId)
            dialog.dismiss()
            backToMainFragment()
        }
        dialog.show()
    }

    private fun setupFabAddNote() {
        binding.fabAddNote.setOnClickListener {
            moveToEditNoteFragment(args.folderId)
        }
    }

    private fun moveToEditNoteFragment(folderId: Long, noteId: Long = -1) {
        val direction =
            NotesOfFolderFragmentDirections.actionNotesOfFolderFragmentToEditNoteFragment(
                folderId = folderId,
                noteId = noteId
            )
        findNavController().navigate(direction)
    }

    private fun backToMainFragment() {
        findNavController().popBackStack()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}