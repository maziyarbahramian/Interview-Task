package com.maziyar.interview.persistence

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

class DatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val date = Date().time
        db.execSQL("INSERT INTO folders(id, name, date) VALUES(1, '/', $date)")
    }
}