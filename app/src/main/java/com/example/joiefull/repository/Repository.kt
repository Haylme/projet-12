package com.example.joiefull.repository

import com.example.joiefull.contentData.Clothes
import com.example.joiefull.contentData.RateContent
import com.example.joiefull.retrofit.CallApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Repository {

    private val _list = mutableListOf<RateContent>()







    private val _rateContentFlow = MutableStateFlow<List<RateContent>>(_list)
    val rateContentFlow : StateFlow<List<RateContent>> get()  = _rateContentFlow

    fun addRating (clotheName: Int, starsRating: Int){

        _list.add(RateContent(clotheName,starsRating))
        _rateContentFlow.value = _list.toList()

    }
    fun getRatings(): List<RateContent> {
        return _rateContentFlow.value
    }



    suspend fun dataFetch(): Clothes{

        return CallApi.fetchData()


    }






}