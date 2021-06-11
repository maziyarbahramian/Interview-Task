package com.maziyar.interview.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = false) var id: Long? = null,
    var folder_id: Long = -1,
    var title: String = "",
    var body: String = "",
    var date: Date? = null
)