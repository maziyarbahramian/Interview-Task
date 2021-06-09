package com.maziyar.interview.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.maziyar.interview.persistence.entities.Folder
import com.maziyar.interview.persistence.entities.Note

@Database(
    entities = [Note::class, Folder::class],
    version = 1
)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao

}