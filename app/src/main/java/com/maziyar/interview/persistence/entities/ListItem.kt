package com.maziyar.interview.persistence.entities

import androidx.room.DatabaseView
import java.util.*

@DatabaseView(
    """
            select id,
                   title as title,
                   date, "" as subTitle,
                   0 as type
            from notes where folder_id=-1 
        union 
            select folders.id,
                   folders.name as title,
                   date, 
                   (select count(id) from notes where folders.id=notes.folder_id) as subTitle,
                   1 as type 
            from folders
        """
)
data class ListItem(
    val id: Long,
    val title: String,
    val date: Date,
    val subTitle: String,
    val type: Int
)
