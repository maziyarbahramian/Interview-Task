package com.maziyar.interview.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maziyar.interview.persistence.entities.Folder
import com.maziyar.interview.persistence.entities.Note
import com.maziyar.interview.persistence.typeConverters.DateConverter

@Database(
    entities = [Note::class, Folder::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao

}