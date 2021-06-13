package com.maziyar.interview.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.maziyar.interview.persistence.DatabaseCallback
import com.maziyar.interview.persistence.NotesDao
import com.maziyar.interview.persistence.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(
        @ApplicationContext context: Context,
        callback: RoomDatabase.Callback
    ): NotesDatabase {
        return Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            "notes_db"
        )
            .addCallback(callback)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNotesDao(notesDatabase: NotesDatabase): NotesDao = notesDatabase.getNotesDao()

    @Provides
    @Singleton
    fun provideOnCreateCallback(): RoomDatabase.Callback {
        return DatabaseCallback()
    }
}