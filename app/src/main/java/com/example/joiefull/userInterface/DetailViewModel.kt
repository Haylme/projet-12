package com.example.joiefull.userInterface

import androidx.lifecycle.ViewModel
import com.example.joiefull.contentData.RateContent
import com.example.joiefull.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    val rateContentFlow: StateFlow<List<RateContent>> = repository.rateContentFlow




    fun rate(clothesId:Int, usersRating: List<RateContent>): Int {

        var sum = 0
        val totalSize = usersRating.size



        for (item in usersRating) {
            if (item.id == clothesId) {

                sum += item.starsRating
            }
        }
        val response = sum / totalSize

        return response
    }




}