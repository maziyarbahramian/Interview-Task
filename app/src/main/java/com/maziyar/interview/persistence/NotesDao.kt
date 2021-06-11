package com.maziyar.interview.persistence

import androidx.room.*
import com.maziyar.interview.persistence.entities.Folder
import com.maziyar.interview.persistence.entities.Note

@Dao
interface NotesDao {

    @Insert
    suspend fun insertFolder(folder: Folder)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long
    
}