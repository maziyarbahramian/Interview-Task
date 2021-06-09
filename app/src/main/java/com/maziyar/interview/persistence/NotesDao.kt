package com.maziyar.interview.persistence

import androidx.room.Dao
import com.maziyar.interview.persistence.entities.Folder
import com.maziyar.interview.persistence.entities.Note

@Dao
interface NotesDao {
    suspend fun insertFolder(folder: Folder)

    suspend fun insertNote(note: Note)
}