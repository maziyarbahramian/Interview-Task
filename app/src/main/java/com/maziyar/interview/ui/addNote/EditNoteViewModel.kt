package com.maziyar.interview.ui.addNote

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maziyar.interview.persistence.entities.Note
import com.maziyar.interview.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import saman.zamani.persiandate.PersianDate
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {
    private val TAG = "EditNoteViewModel"

    private var note: Note = Note()

    fun insertNote() {
        viewModelScope.launch {
            if (note.body.isNotEmpty()) {

                note.id = repository.insertNote(note)

            }
        }
    }

    fun setNote(note: Note) {
        this.note.title = note.title
        this.note.body = note.body
        this.note.folder_id = note.folder_id
        this.note.date = note.date
    }

}