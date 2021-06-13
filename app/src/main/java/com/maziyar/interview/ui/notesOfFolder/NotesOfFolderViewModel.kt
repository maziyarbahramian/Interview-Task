package com.maziyar.interview.ui.notesOfFolder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maziyar.interview.persistence.entities.Folder
import com.maziyar.interview.persistence.entities.Note
import com.maziyar.interview.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesOfFolderViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {

    lateinit var notesOfFolderLiveData: LiveData<List<Note>>

    lateinit var folderLiveData: LiveData<Folder>

    fun getFolder(folderId: Long) {
        folderLiveData = repository.getFolder(folderId)
    }

    fun getNotesOfFolder(folderId: Long) {
        notesOfFolderLiveData = repository.getNotesOfFolder(folderId)
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
        }
    }

    fun deleteFolder(folderId: Long) {
        viewModelScope.launch {
            repository.deleteFolder(folderId)
        }
    }

    fun renameFolder(folderName: String, folderId: Long) {
        viewModelScope.launch {
            repository.renameFolder(folderName, folderId)
        }
    }
}