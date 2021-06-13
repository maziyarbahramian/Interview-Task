package com.maziyar.interview.persistence.entities

import androidx.room.*
import androidx.room.ForeignKey.*
import java.util.*

@Entity(
    tableName = "notes", foreignKeys = [
        ForeignKey(
            entity = Folder::class,
            parentColumns = ["id"],
            childColumns = ["folder_id"],
            onDelete = CASCADE,
        )
    ],
    indices = [Index("folder_id")]
)
data class Note(
    @PrimaryKey(autoGenerate = false) var id: Long? = null,
    var folder_id: Long = 1,
    var title: String = "",
    var body: String = "",
    var date: Date? = null
)