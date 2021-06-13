package com.maziyar.interview.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.maziyar.interview.R
import com.maziyar.interview.databinding.FragmentMainBinding
import com.maziyar.interview.persistence.entities.Folder
import com.maziyar.interview.persistence.views.ListItem
import com.maziyar.interview.ui.customViews.CustomDialog
import com.maziyar.interview.ui.customViews.listPopupWindwo.CustomListPopupWindow
import com.maziyar.interview.ui.customViews.listPopupWindwo.OnPopupMenuItemClickListener
import com.maziyar.interview.ui.customViews.listPopupWindwo.PopupIds
import com.maziyar.interview.ui.main.list.ItemClickListener
import com.maziyar.interview.ui.main.list.MainAdapter
import com.maziyar.interview.utils.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val TAG = "MainFragment"

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()
    private lateinit var recyclerAdapter: MainAdapter

    private var isClickable = false

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.to_bottom_anim
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setupFloatingActionButtons()
        setupToolbar()
        setupRecyclerView()
        viewModel.mainListLiveData.observe(viewLifecycleOwner, {
            Log.i(TAG, "onViewCreated: $it")
            recyclerAdapter.submitList(it)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        recyclerAdapter = MainAdapter(
            folderItemClickListener = ItemClickListener(
                onItemClickListener = { folder ->
                    val direction =
                        MainFragmentDirections.actionMainFragmentToNotesOfFolderFragment(
                            folder.id,
                            folder.title
                        )
                    findNavController().navigate(direction)
                },
                showOverflowMenu = { folderItem, anchorView ->
                    CustomListPopupWindow.getRenameAndDeletePopupWindow(
                        context = requireContext(),
                        anchor = anchorView,
                        popupMenuItemClick = OnPopupMenuItemClickListener { popupItem ->
                            when (popupItem.id) {
                                PopupIds.RENAME -> {
                                    showRenameFolderDialog(folderItem)
                                }
                                PopupIds.DELETE -> {
                                    showDeleteFolderDialog(folderItem.id)
                                }
                            }
                        }
                    ).show()
                }
            ),
            noteItemClickListener = ItemClickListener(
                onItemClickListener = { note ->
                    val direction =
                        MainFragmentDirections.actionMainFragmentToEditNoteFragment(note.id)
                    findNavController().navigate(direction)
                },
                showOverflowMenu = { noteItem, anchorView ->
                    CustomListPopupWindow.getDeletePopupWindow(
                        context = requireContext(),
                        anchor = anchorView,
                        popupMenuItemClick = OnPopupMenuItemClickListener { popupItem ->
                            if (popupItem.id == PopupIds.DELETE) {
                                showDeleteNoteDialog(noteItem.id)
                            }
                        }
                    ).show()
                }
            )
        )

        binding.recyclerView.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(context, 8.0f))
        }
    }

    private fun setupFloatingActionButtons() {
        binding.apply {
            fabAdd.setOnClickListener {
                onFabAddButtonClicked()
            }

            fabAddFolder.setOnClickListener {
                showAddFolderDialog()
            }

            fabAddNote.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_editNoteFragment)
                Log.i(TAG, "add note button clicked")
            }
        }
    }

    private fun onFabAddButtonClicked() {
        setFabButtonsVisibility(isClickable)
        setFabButtonsAnimation(isClickable)
        setFabButtonsClickable(isClickable)
        isClickable = !isClickable
    }

    private fun showDeleteFolderDialog(folderId: Long) {
        val dialog = CustomDialog.getDeleteFolderDialog(requireContext())

        dialog.setAcceptButtonClickListener {
            viewModel.deleteFolder(folderId)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showDeleteNoteDialog(noteId: Long) {
        val dialog = CustomDialog.getDeleteNoteDialog(requireContext())

        dialog.setAcceptButtonClickListener {
            viewModel.deleteNote(noteId)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showRenameFolderDialog(folder: ListItem) {
        val dialog = CustomDialog.getRenameFolderDialog(requireContext())
        dialog.setAcceptButtonClickListener {
            val newName = dialog.getInputText()
            if (newName.isEmpty()) {
                Toast.makeText(
                    context,
                    getString(R.string.please_enter_folder_name),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.renameFolder(newName, folder.id)
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun showAddFolderDialog() {
        val dialog = CustomDialog.getAddFolderDialog(requireContext())

        dialog.setAcceptButtonClickListener {
            val folderName = dialog.getInputText()
            if (folderName.isNotEmpty()) {
                viewModel.insertFolder(Folder(name = folderName, date = Date()))
                dialog.dismiss()
            } else
                Toast.makeText(
                    context,
                    getString(R.string.please_enter_folder_name),
                    Toast.LENGTH_SHORT
                ).show()
        }

        dialog.show()
        onFabAddButtonClicked()
    }


    private fun setFabButtonsVisibility(isClickable: Boolean) {
        if (!isClickable) {
            binding.fabAddNote.visibility = View.VISIBLE
            binding.fabAddFolder.visibility = View.VISIBLE
        } else {
            binding.fabAddNote.visibility = View.INVISIBLE
            binding.fabAddFolder.visibility = View.INVISIBLE
        }
    }

    private fun setFabButtonsAnimation(isClickable: Boolean) {
        if (!isClickable) {
            binding.fabAddNote.startAnimation(fromBottom)
            binding.fabAddFolder.startAnimation(fromBottom)
            binding.fabAdd.startAnimation(rotateOpen)
        } else {
            binding.fabAddNote.startAnimation(toBottom)
            binding.fabAddFolder.startAnimation(toBottom)
            binding.fabAdd.startAnimation(rotateClose)
        }
    }

    private fun setFabButtonsClickable(isClickable: Boolean) {
        binding.apply {
            fabAddFolder.isClickable = !isClickable
            fabAddNote.isClickable = !isClickable
        }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            titleTextView.visibility = View.VISIBLE
            titleTextView.text = getString(R.string.notes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}