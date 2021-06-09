package com.maziyar.interview.repository

import android.util.Log
import com.maziyar.interview.persistence.NotesDao
import javax.inject.Inject


class Repository
@Inject
constructor(
    private val notesDao: NotesDao
) {
    private val TAG = "Repository"

}