package com.maziyar.interview.ui.editNote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maziyar.interview.persistence.entities.Note
import com.maziyar.interview.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel
@Inject
constructor(
    private val repository: Repository
) : ViewModel() {
    private val TAG = "EditNoteViewModel"

    val existingNoteLiveData: MutableLiveData<Note> by lazy {
        MutableLiveData()
    }

    private var note: Note = Note()

    fun insertNote() {
        viewModelScope.launch {
            if (note.body.isNotEmpty()) {

                note.id = repository.insertNote(note)

            }
        }
    }

    fun getExistingNote(noteId: Long) {
        viewModelScope.launch {
            existingNoteLiveData.value = repository.getNoteById(noteId)
        }
    }

    fun setNote(note: Note) {

        this.note.apply {
            title = note.title
            body = note.body
            id = if (id == null) note.id else id
            date = if (date == null) note.date else date
        }
    }

}