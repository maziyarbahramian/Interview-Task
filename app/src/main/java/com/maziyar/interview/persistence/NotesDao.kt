package com.maziyar.interview.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import com.maziyar.interview.persistence.entities.Folder
import com.maziyar.interview.persistence.views.ListItem
import com.maziyar.interview.persistence.entities.Note

@Dao
interface NotesDao {

    @Insert
    suspend fun insertFolder(folder: Folder)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long

    @Query("SELECT * FROM ListItem ORDER BY date DESC")
    fun getMainListItems(): LiveData<List<ListItem>>

    @Query("SELECT * FROM notes WHERE id=:noteId")
    suspend fun getNoteById(noteId: Long): Note

    @Query("SELECT * FROM notes WHERE folder_id=:folderId")
    fun getNotesOfFolder(folderId: Long): LiveData<List<Note>>

    @Query("DELETE FROM notes WHERE id=:noteId")
    suspend fun deleteNote(noteId: Long)

    @Query("UPDATE folders SET name=:folderName WHERE id=:folderId")
    suspend fun renameFolder(folderName: String, folderId: Long): Int

    @Query("Delete FROM folders WHERE id=:folderId")
    suspend fun deleteFolder(folderId: Long)

}