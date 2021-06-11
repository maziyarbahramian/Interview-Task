package com.maziyar.interview.repository

import android.util.Log
import com.maziyar.interview.persistence.NotesDao
import com.maziyar.interview.persistence.entities.Folder
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

}