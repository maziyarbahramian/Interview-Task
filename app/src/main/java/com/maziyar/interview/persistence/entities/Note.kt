package com.maziyar.interview.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey val id: Long? = null,
    val folder_id: Long = -1,
    val title: String,
    val body: String,
//    val date: Date
)