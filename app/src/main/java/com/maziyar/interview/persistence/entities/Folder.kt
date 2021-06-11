package com.maziyar.interview.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "folders")
data class Folder(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var name: String = "",
    var date: Date? = null
)