package com.maziyar.interview.ui.notesOfFolder

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.maziyar.interview.persistence.entities.Note
import com.maziyar.interview.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesOfFolderViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {

    lateinit var notesOfFolderLiveData: LiveData<List<Note>>

    fun getNotesOfFolder(folderId: Long) {
        notesOfFolderLiveData = repository.getNotesOfFolder(folderId)
    }
}