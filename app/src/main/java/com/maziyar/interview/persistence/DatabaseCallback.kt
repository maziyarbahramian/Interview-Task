package com.maziyar.interview.persistence

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*

class DatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        val date = Date().time
        db.execSQL("INSERT INTO folders(id, name, date) VALUES(1, '/', $date)")
        val firstNoteBody =
            "<p dir=\"rtl\">&#1575;&#1608;&#1604;&#1740;&#1606; <b>&#1740;&#1575;&#1583;&#1583;&#1575;&#1588;&#1578;</b> &#1582;&#1608;&#1583; <i>&#1585;&#1575;</i> <i>&#1576;&#1606;&#1608;&#1740;&#1587;&#1740;&#1583;</i>.</p> "
        db.execSQL("INSERT INTO notes(id,folder_id,title,body,date) VALUES(1,1,'اولین یادداشت','$firstNoteBody',$date)")
        db.execSQL("INSERT INTO folders(id, name, date) VALUES(2, 'پوشه ی اول', $date)")
    }
}