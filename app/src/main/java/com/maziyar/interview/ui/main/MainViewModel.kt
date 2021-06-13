package com.maziyar.interview.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maziyar.interview.persistence.entities.Folder
import com.maziyar.interview.persistence.views.ListItem
import com.maziyar.interview.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {
    private val TAG = "MainViewModel"

    val mainListLiveData: LiveData<List<ListItem>> by lazy {
        repository.getMainListItems()
    }

    fun insertFolder(folder: Folder) {
        viewModelScope.launch {
            repository.insertFolder(folder)
        }
    }

    fun renameFolder(folderName: String, folderId: Long) {
        viewModelScope.launch {
            val returnt = repository.renameFolder(folderName, folderId)
            Log.i(TAG, "renameFolder: $returnt")
        }
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
}