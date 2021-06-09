package com.maziyar.interview.persistence

import androidx.room.Dao
import androidx.room.Insert
import com.maziyar.interview.persistence.entities.Folder
import com.maziyar.interview.persistence.entities.Note

@Dao
interface NotesDao {

    @Insert
    suspend fun insertFolder(folder: Folder)

    @Insert
    suspend fun insertNote(note: Note)
}