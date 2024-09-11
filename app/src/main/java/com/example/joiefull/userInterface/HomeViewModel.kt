package com.example.joiefull.userInterface

import androidx.collection.intFloatMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.joiefull.contentData.Clothes
import com.example.joiefull.contentData.RateContent
import com.example.joiefull.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private val _fullData = MutableStateFlow(Clothes())
    val fullData: StateFlow<Clothes> = _fullData.asStateFlow()


    fun fetchAll() {

        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.dataFetch()


            if (response.isNotEmpty()) {

                _fullData.value = response

            }


        }


    }


    fun rate(name: String, usersRating: List<RateContent>): Int {

        var sum = 0
        val totalSize = usersRating.size

        for (item in usersRating) {
            if (item.clotheName == name) {

                sum += item.starsRating
            }
        }
        val response = sum / totalSize

        return response
    }


}