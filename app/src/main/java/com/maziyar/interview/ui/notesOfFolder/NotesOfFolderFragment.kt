package com.maziyar.interview.ui.notesOfFolder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.maziyar.interview.databinding.FragmentNotesOfFolderBinding
import com.maziyar.interview.ui.main.list.ItemClickListener
import com.maziyar.interview.utils.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesOfFolderFragment : Fragment() {
    private val TAG = "NotesOfFolderFragment"

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
        setupToolbar()
        setupFabAddNote()
        setupRecyclerView()
        viewModel.getNotesOfFolder(args.folderId)
        viewModel.notesOfFolderLiveData.observe(viewLifecycleOwner, {
            Log.i(TAG, "onCreateView: $it")
            recyclerAdapter.submitList(it)
        })
        return binding.root
    }

    private fun setupRecyclerView() {
        recyclerAdapter = NotesAdapter(
            noteItemClickListener = ItemClickListener(
                onItemClickListener = { note ->
                    val direction =
                        NotesOfFolderFragmentDirections.actionNotesOfFolderFragmentToEditNoteFragment(
                            note.id!!,
                            note.folder_id
                        )
                    findNavController().navigate(direction)
                },
                showOverflowMenu = { note, anchorView ->

                }
            )
        )
        binding.notesRecyclerView.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(SpaceItemDecoration(context, 8.0f))
        }
    }

    private fun setupFabAddNote() {
        binding.fabAddNote.setOnClickListener {
            val direction =
                NotesOfFolderFragmentDirections.actionNotesOfFolderFragmentToEditNoteFragment(
                    folderId = args.folderId
                )
            findNavController().navigate(direction)
        }
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            titleTextView.visibility = View.VISIBLE
            titleTextView.text = args.folderTitle

            menuButton.visibility = View.VISIBLE

            backButton.visibility = View.VISIBLE
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}