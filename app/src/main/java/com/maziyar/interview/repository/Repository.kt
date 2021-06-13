package com.maziyar.interview.repository

import androidx.lifecycle.LiveData
import com.maziyar.interview.persistence.NotesDao
import com.maziyar.interview.persistence.entities.Folder
import com.maziyar.interview.persistence.views.ListItem
import com.maziyar.interview.persistence.entities.Note
import javax.inject.Inject


class Repository
@Inject
constructor(
    private val notesDao: NotesDao
) {
    private val TAG = "Repository"

    suspend fun insertFolder(folder: Folder) {
        notesDao.insertFolder(folder)
    }

    suspend fun insertNote(note: Note): Long {
        return notesDao.insertNote(note)
    }

    fun getMainListItems(): LiveData<List<ListItem>> {
        return notesDao.getMainListItems()
    }

    suspend fun getNoteById(noteId: Long): Note {
        return notesDao.getNoteById(noteId)
    }

    fun getNotesOfFolder(folderId: Long): LiveData<List<Note>> {
        return notesDao.getNotesOfFolder(folderId)
    }

    suspend fun renameFolder(folderName: String, folderId: Long):Int {
        return notesDao.renameFolder(folderName, folderId)
    }

    suspend fun deleteNote(noteId: Long) {
        return notesDao.deleteNote(noteId)
    }

    suspend fun deleteFolder(folderId: Long) {
        return notesDao.deleteFolder(folderId)
    }
}