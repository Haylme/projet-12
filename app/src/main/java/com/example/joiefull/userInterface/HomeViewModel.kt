package com.example.joiefull.userInterface

import androidx.lifecycle.ViewModel
import com.example.joiefull.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    // TODO: Implement the ViewModel
}