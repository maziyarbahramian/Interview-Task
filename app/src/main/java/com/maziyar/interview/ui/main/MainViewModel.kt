package com.maziyar.interview.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.maziyar.interview.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: Repository
):ViewModel() {
    private val TAG = "MainViewModel"


}