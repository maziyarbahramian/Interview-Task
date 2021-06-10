package com.maziyar.interview.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maziyar.interview.persistence.entities.Folder
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

    fun insertFolder(folder: Folder) {
        viewModelScope.launch {
            repository.insertFolder(folder)
        }
    }

}